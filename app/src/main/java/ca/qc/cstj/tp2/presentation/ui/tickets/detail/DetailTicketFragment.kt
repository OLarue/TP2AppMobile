package ca.qc.cstj.tp2.presentation.ui.tickets.detail

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import ca.qc.cstj.tp2.R
import ca.qc.cstj.tp2.core.Resource
import ca.qc.cstj.tp2.data.repositories.GatewayRepository
import ca.qc.cstj.tp2.data.repositories.TicketRepository
import ca.qc.cstj.tp2.databinding.FragmentDetailTicketBinding
import ca.qc.cstj.tp2.presentation.ui.tickets.TicketsViewModel
import com.bumptech.glide.Glide

class DetailTicketFragment : Fragment(R.layout.fragment_detail_ticket){

    private val binding: FragmentDetailTicketBinding by viewBinding()
    private val viewModel: DetailTicketViewModel by viewModels{
        DetailTicketViewModel.Factory(args.href)
    }
    private val args : DetailTicketFragmentArgs by navArgs()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
                    val customerFullName = customer.firstName.plus(" ").plus(customer.lastName)

                    with(binding){
                        tvCustomerName.text = customerFullName
                        tvAdress.text = customer.address
                        tvCity.text = customer.city
                    }
//                    Glide.with(this)


                    (requireActivity() as AppCompatActivity).supportActionBar?.title = "Ticket ".plus(ticket.ticketNumber)
                }
            }
        }
    }


}