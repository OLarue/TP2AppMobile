package ca.qc.cstj.tp2.presentation.ui.gateways.detail

import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import ca.qc.cstj.tp2.R
import ca.qc.cstj.tp2.databinding.FragmentDetailGatewayBinding

class DetailGatewayFragment : Fragment(R.layout.fragment_detail_gateway){
    private val binding: FragmentDetailGatewayBinding by viewBinding()
    private val viewModel: DetailGatewayViewModel by viewModels{
        DetailGatewayViewModel.Factory(args.href)
    }

    private val args : DetailGatewayFragmentArgs by navArgs()

}