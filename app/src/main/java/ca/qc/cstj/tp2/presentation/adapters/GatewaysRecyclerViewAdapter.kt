package ca.qc.cstj.tp2.presentation.adapters

import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ca.qc.cstj.tp2.R
import ca.qc.cstj.tp2.core.ColorHelper
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
            with(binding) {
                val context = root.context
                chpStatusGateway.text = gateway.connection.status
                chpStatusGateway.chipBackgroundColor = ColorHelper.connectionStatusColor(context, gateway.connection.status)

                if (gateway.connection.status == "Online") {
                    txvOffline.visibility = View.INVISIBLE
                    grpInfoGateway.visibility = View.VISIBLE
                } else {
                    txvOffline.visibility = View.VISIBLE
                    grpInfoGateway.visibility = View.INVISIBLE
                }

                txvPing.text = context.getString(R.string.pingString, gateway.connection.ping)
                txvDownload.text = context.getString(R.string.ebpsString, gateway.connection.download)
                txvUpload.text = context.getString(R.string.ebpsString, gateway.connection.upload)

                txvSerialNumber.text = gateway.serialNumber
            }
        }
    }
}