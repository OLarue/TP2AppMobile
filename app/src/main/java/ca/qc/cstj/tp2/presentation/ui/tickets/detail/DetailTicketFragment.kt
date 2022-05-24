package ca.qc.cstj.tp2.presentation.ui.tickets.detail

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ca.qc.cstj.tp2.R
import ca.qc.cstj.tp2.databinding.FragmentDetailTicketBinding

class DetailTicketFragment : Fragment(R.layout.fragment_detail_ticket){

    private val binding: FragmentDetailTicketBinding by viewBinding()
    private val viewModel: DetailTicketViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


}