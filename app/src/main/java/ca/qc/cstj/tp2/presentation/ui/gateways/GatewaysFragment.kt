package ca.qc.cstj.tp2.presentation.ui.gateways

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import ca.qc.cstj.tp2.R
import ca.qc.cstj.tp2.core.LoadingResource
import ca.qc.cstj.tp2.core.notifyAllItemChanged
import ca.qc.cstj.tp2.databinding.FragmentGatewayBinding
import ca.qc.cstj.tp2.domain.models.*
import ca.qc.cstj.tp2.presentation.adapters.GatewaysRecyclerViewAdapter
import ca.qc.cstj.tp2.presentation.ui.tickets.TicketsFragmentDirections

class GatewaysFragment : Fragment(R.layout.fragment_gateway) {
    private val binding by viewBinding<FragmentGatewayBinding>()
    private val viewModel by viewModels<GatewaysViewModel>()

    private lateinit var  gatewaysRecyclerViewAdapter: GatewaysRecyclerViewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        (requireActivity() as AppCompatActivity).supportActionBar?.title = getString(R.string.gatewaysTitle)

        gatewaysRecyclerViewAdapter = GatewaysRecyclerViewAdapter(listOf(), ::onRecyclerViewGatewayClick)

        binding.rcvGateways.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = gatewaysRecyclerViewAdapter
        }

        viewModel.gateways.observe(viewLifecycleOwner) {
            when(it) {
                is LoadingResource.Error -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                }
                is LoadingResource.Loading -> {
                    binding.pgbLoading.show()
                    binding.rcvGateways.visibility = View.INVISIBLE
                }
                is LoadingResource.Success -> {
                    gatewaysRecyclerViewAdapter.gateways = it.data!!
                    gatewaysRecyclerViewAdapter.notifyAllItemChanged()
                    binding.pgbLoading.hide()
                    binding.rcvGateways.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun onRecyclerViewGatewayClick(gateway: Gateway) {
        val direction = GatewaysFragmentDirections.actionNavigationGatewaysToNavigationDetailGatewayFragment(gateway.href)
        findNavController().navigate(direction)
    }
}