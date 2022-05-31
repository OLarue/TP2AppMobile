package ca.qc.cstj.tp2.presentation.ui.network

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import ca.qc.cstj.tp2.R
import ca.qc.cstj.tp2.core.LoadingResource
import ca.qc.cstj.tp2.core.notifyAllItemChanged
import ca.qc.cstj.tp2.databinding.FragmentNetworkBinding
import ca.qc.cstj.tp2.presentation.adapters.NetworkRecyclerViewAdapter

class NetworkFragment : Fragment(R.layout.fragment_network) {
    private val binding by viewBinding<FragmentNetworkBinding>()
    private val viewModel by viewModels<NetworkViewModel>()

    private lateinit var networkRecyclerViewAdapter: NetworkRecyclerViewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        networkRecyclerViewAdapter = NetworkRecyclerViewAdapter()

        binding.rcvNetworks.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = networkRecyclerViewAdapter
        }

        viewModel.networks.observe(viewLifecycleOwner) {
            when (it){
                is LoadingResource.Error -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                }
                is LoadingResource.Loading -> {
                    binding.pgbLoading.show()
                    binding.rcvNetworks.visibility = View.INVISIBLE
                }
                is LoadingResource.Success ->  {
                    networkRecyclerViewAdapter.networks = it.data!!
                    networkRecyclerViewAdapter.notifyAllItemChanged()
                    binding.pgbLoading.hide()
                    binding.rcvNetworks.visibility = View.VISIBLE
                }
            }
        }

    }

}