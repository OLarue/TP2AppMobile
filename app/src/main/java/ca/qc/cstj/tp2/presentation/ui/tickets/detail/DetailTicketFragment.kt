package ca.qc.cstj.tp2.presentation.ui.tickets.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import ca.qc.cstj.tp2.R
import ca.qc.cstj.tp2.core.*
import ca.qc.cstj.tp2.data.repositories.GatewayRepository
import ca.qc.cstj.tp2.data.repositories.TicketRepository
import ca.qc.cstj.tp2.databinding.FragmentDetailTicketBinding
import ca.qc.cstj.tp2.domain.models.Customer
import ca.qc.cstj.tp2.domain.models.Gateway
import ca.qc.cstj.tp2.domain.models.Ticket
import ca.qc.cstj.tp2.presentation.adapters.GatewaysRecyclerViewAdapter
import ca.qc.cstj.tp2.presentation.adapters.TicketsRecyclerViewAdapter
import ca.qc.cstj.tp2.presentation.ui.tickets.TicketsFragmentDirections
import ca.qc.cstj.tp2.presentation.ui.tickets.TicketsViewModel
import com.bumptech.glide.Glide

class DetailTicketFragment : Fragment(R.layout.fragment_detail_ticket){

    private val binding: FragmentDetailTicketBinding by viewBinding()
    private val viewModel: DetailTicketViewModel by viewModels{
        DetailTicketViewModel.Factory(args.href)
    }
    private val args : DetailTicketFragmentArgs by navArgs()

    lateinit var gatewaysRecyclerViewAdapter: GatewaysRecyclerViewAdapter


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
                   //TODO()
                }
                is LoadingResource.Success -> {
                    gatewaysRecyclerViewAdapter.gateways = it.data!!
                    gatewaysRecyclerViewAdapter.notifyAllItemChanged()
                }
            }
        }

        binding.btnSolve.setOnClickListener {
            //TODO
        }

        binding.btnInstall.setOnClickListener{
            //TODO
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
    }

    private fun onRecyclerViewGatewayClick(gateway: Gateway) {
        val direction = DetailTicketFragmentDirections.actionNavigationDetailTicketFragmentToNavigationDetailGatewayFragment(gateway.href)
        findNavController().navigate(direction)
    }

}