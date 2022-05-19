package ca.qc.cstj.tp2.presentation.ui.tickets

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ca.qc.cstj.tp2.R
import ca.qc.cstj.tp2.databinding.FragmentTicketsBinding
import ca.qc.cstj.tp2.presentation.adapters.TicketsRecyclerViewAdapter

class TicketsFragment : Fragment(R.layout.fragment_tickets) {

    private val binding: FragmentTicketsBinding by viewBinding()
    private val viewModel: TicketsViewModel by viewModels()

    lateinit var ticketsRecyclerViewAdapter: TicketsRecyclerViewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

}