package ca.qc.cstj.tp2.presentation.ui.tickets.detail

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import ca.qc.cstj.tp2.R
import ca.qc.cstj.tp2.core.*
import ca.qc.cstj.tp2.databinding.FragmentDetailTicketBinding
import ca.qc.cstj.tp2.domain.models.Customer
import ca.qc.cstj.tp2.domain.models.Gateway
import ca.qc.cstj.tp2.domain.models.Ticket
import ca.qc.cstj.tp2.presentation.adapters.GatewaysRecyclerViewAdapter
import com.bumptech.glide.Glide
import com.google.android.gms.maps.model.LatLng
import io.github.g00fy2.quickie.QRResult
import io.github.g00fy2.quickie.ScanQRCode

class DetailTicketFragment : Fragment(R.layout.fragment_detail_ticket){

    private val binding: FragmentDetailTicketBinding by viewBinding()
    private val viewModel: DetailTicketViewModel by viewModels{
        DetailTicketViewModel.Factory(args.href)
    }
    private val args : DetailTicketFragmentArgs by navArgs()

    lateinit var gatewaysRecyclerViewAdapter: GatewaysRecyclerViewAdapter

    private var position: LatLng? = null
    private var customerName: String = "nom"

    private var quickieActivityLauncher = registerForActivityResult(ScanQRCode(), ::handleQuickieResult)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gatewaysRecyclerViewAdapter = GatewaysRecyclerViewAdapter(listOf(), ::onRecyclerViewGatewayClick)

        binding.rcvCustomerGateways.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = gatewaysRecyclerViewAdapter
        }

        viewModel.ticket.observe(viewLifecycleOwner) {
            when(it){
                is Resource.Error -> {
                    Toast.makeText(requireContext(), it.throwable.message, Toast.LENGTH_LONG).show()
                    //pour revenir au fragment d'avant
                    requireActivity().supportFragmentManager.popBackStack()
                }
                is Resource.Success -> {
                    val ticket = it.data!!
                    val customer = ticket.customer

                    showTicketInfo(ticket)
                    showCustomerInfo(customer)
                    (requireActivity() as AppCompatActivity).supportActionBar?.title = "Ticket ".plus(ticket.ticketNumber)
                }
            }
        }

        viewModel.gateways.observe(viewLifecycleOwner) {
            when(it) {
                is LoadingResource.Error -> {
                    Toast.makeText(requireContext(),it.message, Toast.LENGTH_LONG).show()
                }
                is LoadingResource.Loading -> {
                    binding.pgbGatewaysLoading.show()
                    binding.rcvCustomerGateways.visibility = View.INVISIBLE
                }
                is LoadingResource.Success -> {
                    gatewaysRecyclerViewAdapter.gateways = it.data!!
                    gatewaysRecyclerViewAdapter.notifyAllItemChanged()
                    binding.pgbGatewaysLoading.hide()
                    binding.rcvCustomerGateways.visibility = View.VISIBLE
                }
            }
        }

        viewModel.installedGateway.observe(viewLifecycleOwner) {
            when(it) {
                is Resource.Error -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                }
                is Resource.Success -> {
                    viewModel.addGateway(it.data!!)
                    gatewaysRecyclerViewAdapter.notifyAllItemChanged()
                }
            }
        }

        binding.btnSolve.setOnClickListener {
            //TODO
            viewModel.changeTicketStatus()
        }

        binding.btnInstall.setOnClickListener{
            quickieActivityLauncher.launch(null)
        }

        binding.btnOpen!!.setOnClickListener {
        // Mais pourquoi le bouton est nullable???
        //TODO
            viewModel.changeTicketStatus()
        }

        binding.fabLocation.setOnClickListener{
            val action = DetailTicketFragmentDirections
                .actionNavigationDetailTicketFragmentToMapsActivity(position!!,customerName)

            findNavController().navigate(action)

        }

    }

    private fun showTicketInfo(ticket: Ticket){
        with(binding) {
            val ticketCreateDate = DateHelper.formatISODate(ticket.createdDate)

            includeTicket.txvTicketNumber.text = ticket.ticketNumber
            includeTicket.txvTicketCreatedDate.text = ticketCreateDate

            includeTicket.chpTicketStatus.chipBackgroundColor = ColorHelper.ticketStatusColor(
                root.context,
                ticket.status

            )
            includeTicket.chpTicketPriority.chipBackgroundColor = ColorHelper.ticketPriorityColor(
                root.context,
                ticket.priority
            )

            includeTicket.chpTicketStatus.text = ticket.status
            includeTicket.chpTicketPriority.text = ticket.priority
        }

        if (ticket.status == "Open")
        {
            with(binding){
                btnOpen!!.visibility = View.INVISIBLE
                btnInstall.visibility = View.VISIBLE
                btnSolve.visibility = View.VISIBLE
            }
        } else {
            with(binding){
                btnOpen!!.visibility = View.VISIBLE
                btnInstall.visibility = View.INVISIBLE
                btnSolve.visibility = View.INVISIBLE
            }
        }

    }

    private fun showCustomerInfo(customer: Customer){
        with(binding){
            tvCustomerName.text = customer.firstName.plus(" ").plus(customer.lastName)
            tvAdress.text = customer.address
            tvCity.text = customer.city
            Glide.with(requireContext())
                .load(Constants.FLAG_API_URL.format(customer.country.lowercase()))
                .into(binding.imvCountryFlag)

        }
        position = LatLng(customer.coord.latitude.toDouble(), customer.coord.longitude.toDouble())
        customerName = customer.firstName.plus(" ").plus(customer.lastName)

    }

    private fun onRecyclerViewGatewayClick(gateway: Gateway) {
        val direction = DetailTicketFragmentDirections.actionNavigationDetailTicketFragmentToNavigationDetailGatewayFragment(gateway.href)
        findNavController().navigate(direction)
    }

    private fun handleQuickieResult(qrResult: QRResult?) {
        lateinit var routerJsonData: String
        when(qrResult) {
            is QRResult.QRSuccess -> {
                routerJsonData = qrResult.content.rawValue
                viewModel.installRouter(routerJsonData)
            }
            is QRResult.QRUserCanceled -> {
                Toast.makeText(requireContext(), getString(R.string.user_canceled), Toast.LENGTH_LONG).show()
            }
            is QRResult.QRMissingPermission -> {
                Toast.makeText(requireContext(), getString(R.string.missing_permission), Toast.LENGTH_LONG).show()
            }
            is QRResult.QRError -> {
                Toast.makeText(requireContext(), qrResult.exception.message, Toast.LENGTH_LONG).show()
            }
        }
    }

}