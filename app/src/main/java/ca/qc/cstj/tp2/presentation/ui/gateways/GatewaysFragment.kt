package ca.qc.cstj.tp2.presentation.ui.gateways

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import ca.qc.cstj.tp2.R
import ca.qc.cstj.tp2.core.LoadingResource
import ca.qc.cstj.tp2.core.notifyAllItemChanged
import ca.qc.cstj.tp2.databinding.FragmentGatewayBinding
import ca.qc.cstj.tp2.domain.models.*
import ca.qc.cstj.tp2.presentation.adapters.GatewaysRecyclerViewAdapter

class GatewaysFragment : Fragment(R.layout.fragment_gateway) {
    private val binding by viewBinding<FragmentGatewayBinding>()
    private val viewModel by viewModels<GatewaysViewModel>()

    private lateinit var  gatewaysRecyclerViewAdapter: GatewaysRecyclerViewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gatewaysRecyclerViewAdapter = GatewaysRecyclerViewAdapter(listOf(
            Gateway(
                "0",
                "083748357",
                "9272",
                "9703",
                "9027820872",
                Connection("s", "s", 20F, 20F, 20, 20),
                Config("-", ",", "2", listOf(), 1),
                Customer("a", "s", "s", ",", ",", ",", ",", ",", Coordinate(20F, 20F))
            )
        ), ::onRecyclerViewGatewayClick)

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

                }
                is LoadingResource.Success -> {
                    gatewaysRecyclerViewAdapter.gateways = it.data!!
                    gatewaysRecyclerViewAdapter.notifyAllItemChanged()
                }
            }
        }
    }

    private fun onRecyclerViewGatewayClick(gateway: Gateway) {

    }
}