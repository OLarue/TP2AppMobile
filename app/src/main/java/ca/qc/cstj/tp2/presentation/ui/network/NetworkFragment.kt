package ca.qc.cstj.tp2.presentation.ui.network

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import ca.qc.cstj.tp2.R
import ca.qc.cstj.tp2.core.DateHelper
import ca.qc.cstj.tp2.core.LoadingResource
import ca.qc.cstj.tp2.core.notifyAllItemChanged
import ca.qc.cstj.tp2.databinding.FragmentNetworkBinding
import ca.qc.cstj.tp2.domain.models.Network
import ca.qc.cstj.tp2.presentation.adapters.NetworkNodesRecyclerViewAdapter
import java.time.format.DateTimeFormatter
import java.util.*

class NetworkFragment : Fragment(R.layout.fragment_network) {
    private val binding by viewBinding<FragmentNetworkBinding>()
    private val viewModel by viewModels<NetworkViewModel>()

    private lateinit var networkNodesRecyclerViewAdapter: NetworkNodesRecyclerViewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        networkNodesRecyclerViewAdapter = NetworkNodesRecyclerViewAdapter()

        binding.rcvNetworks.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = networkNodesRecyclerViewAdapter
        }

        viewModel.network.observe(viewLifecycleOwner) {
            when (it){
                is LoadingResource.Error -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                }
                is LoadingResource.Loading -> {
                    binding.pgbLoading.show()
                    binding.rcvNetworks.visibility = View.INVISIBLE
                }
                is LoadingResource.Success ->  {
                    val network = it.data!!

                    networkNodesRecyclerViewAdapter.nodes = network.nodes
                    networkNodesRecyclerViewAdapter.notifyAllItemChanged()
                    binding.pgbLoading.hide()

                    showNetworkDetail(network)
                    binding.rcvNetworks.visibility = View.VISIBLE
                }
            }
        }
    }
    private fun showNetworkDetail(network : Network){

        with(binding){
            val nextRebootDate = DateHelper.formatISODate(network.nextReboot)
            val lastUpdateDate = DateHelper.formatISODate(network.updateDate)

            txvNextReboot.text = getString(R.string.txvReboot).plus(" ").plus(nextRebootDate)
            txvLastUpdate.text = getString(R.string.txvUpdate).plus(" ").plus(lastUpdateDate)
        }
    }

}