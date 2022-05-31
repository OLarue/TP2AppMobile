package ca.qc.cstj.tp2.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ca.qc.cstj.tp2.databinding.ItemNodeBinding
import ca.qc.cstj.tp2.domain.models.NetworkNode

class NetworkNodesRecyclerViewAdapter(var nodes: List<NetworkNode> = listOf())
    : RecyclerView.Adapter<NetworkNodesRecyclerViewAdapter.ViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemNodeBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val node = nodes[position]
        holder.bind(node)
    }

    override fun getItemCount() = nodes.size


    inner class ViewHolder(private val binding: ItemNodeBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(node: NetworkNode) {
            showNodeInfo(node, binding)
        }
    }

    private fun showNodeInfo(node: NetworkNode, binding: ItemNodeBinding){
        with(binding){
            val connection = node.connection

            txvNode.text = node.name
            txvIpAdresse.text = connection.ip
            txvLatency.text = connection.ping.toString().plus(ca.qc.cstj.tp2.core.Constants.NodeMetrics.LATENCY)
            txvSignalQuality.text = connection.signal.toString().plus(ca.qc.cstj.tp2.core.Constants.NodeMetrics.SIGNAL)
            txvNetworkUpload.text = connection.upload.toString().plus(ca.qc.cstj.tp2.core.Constants.NodeMetrics.UPLOAD)
            txvNetworkDownload.text = connection.download.toString().plus(ca.qc.cstj.tp2.core.Constants.NodeMetrics.DOWNLOAD)

            chpNetworkStatus.text = connection.status
            chpNetworkStatus.chipBackgroundColor = ca.qc.cstj.tp2.core.ColorHelper.connectionStatusColor(root.context, connection.status)

        }
    }
}