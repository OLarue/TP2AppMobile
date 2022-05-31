package ca.qc.cstj.tp2.presentation.adapters

import android.net.Network
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ca.qc.cstj.tp2.databinding.ItemNetworkBinding

class NetworkRecyclerViewAdapter(var networks: List<Network> = listOf())
    : RecyclerView.Adapter<NetworkRecyclerViewAdapter.ViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemNetworkBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val network = networks[position]
        holder.bind(network)
    }

    override fun getItemCount() = networks.size


    inner class ViewHolder(private val binding: ItemNetworkBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(network: Network) {

        }
    }

//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        TODO("Not yet implemented")
//    }
}