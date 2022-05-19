package ca.qc.cstj.tp2.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ca.qc.cstj.tp2.databinding.ItemGatewayBinding
import ca.qc.cstj.tp2.domain.models.Gateway

class GatewaysRecyclerViewAdapter(
    var gateways: List<Gateway> = listOf(),
    private val onGatewayClick: (Gateway) -> Unit)
: RecyclerView.Adapter<GatewaysRecyclerViewAdapter.ViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemGatewayBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val gateway = gateways[position]
        holder.bind(gateway)

        holder.itemView.setOnClickListener {
            onGatewayClick(gateway)
        }
    }

    override fun getItemCount() = gateways.size

    inner class ViewHolder(private val binding: ItemGatewayBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(gateway: Gateway) {

        }
    }
}