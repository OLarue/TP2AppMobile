package ca.qc.cstj.tp2.presentation.ui.tickets

import android.os.Bundle
import android.util.Log
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ca.qc.cstj.tp2.R
import ca.qc.cstj.tp2.core.LoadingResource
import ca.qc.cstj.tp2.core.Resource
import ca.qc.cstj.tp2.core.notifyAllItemChanged
import ca.qc.cstj.tp2.databinding.FragmentTicketsBinding
import ca.qc.cstj.tp2.domain.models.Ticket
import ca.qc.cstj.tp2.presentation.adapters.TicketsRecyclerViewAdapter

class TicketsFragment : Fragment(R.layout.fragment_tickets) {

    private val binding: FragmentTicketsBinding by viewBinding()
    private val viewModel: TicketsViewModel by viewModels()

    lateinit var ticketsRecyclerViewAdapter: TicketsRecyclerViewAdapter



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ticketsRecyclerViewAdapter = TicketsRecyclerViewAdapter(listOf(), ::onRecyclerViewTicketClick)

        binding.rcvTickets.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = ticketsRecyclerViewAdapter
        }

        viewModel.ticketsLoading.observe(viewLifecycleOwner){
            when(it){
                is LoadingResource.Error -> {
                    Toast.makeText(requireContext(),it.message, Toast.LENGTH_LONG).show()
                    Log.d("Erreur loading", it.message!!)
                }
                is LoadingResource.Loading -> {
                    binding.pgbTicketLoading.show()
                    binding.rcvTickets.visibility = View.INVISIBLE
                }
                is LoadingResource.Success -> {
                    binding.pgbTicketLoading.hide()
                    binding.rcvTickets.visibility = View.VISIBLE

                    ticketsRecyclerViewAdapter.tickets = it.data!!
                    ticketsRecyclerViewAdapter.notifyAllItemChanged()

                }
            }
        }
    }

    private fun onRecyclerViewTicketClick(ticket: Ticket){
        val direction = TicketsFragmentDirections.actionNavigationTicketsToNavigationDetailTicketFragment(ticket.href)
        findNavController().navigate(direction)
    }

}