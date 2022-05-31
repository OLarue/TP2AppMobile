package ca.qc.cstj.tp2.presentation.ui.network

import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ca.qc.cstj.tp2.R
import ca.qc.cstj.tp2.databinding.FragmentNetworkBinding

class NetworkFragment : Fragment(R.layout.fragment_network) {
    private val binding by viewBinding<FragmentNetworkBinding>()
    private val viewModel by viewModels<NetworkViewModel>()


}