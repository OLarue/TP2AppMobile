package ca.qc.cstj.tp2.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ca.qc.cstj.tp2.core.ColorHelper
import ca.qc.cstj.tp2.databinding.FragmentTicketsBinding
import ca.qc.cstj.tp2.databinding.ItemTicketBinding
import ca.qc.cstj.tp2.domain.models.Ticket


class TicketsRecyclerViewAdapter(
    var tickets: List<Ticket> = listOf(),
    private val onTicketClick: (Ticket) -> Unit)
    : RecyclerView.Adapter<TicketsRecyclerViewAdapter.ViewHolder>()  {


    inner class ViewHolder(private val binding: ItemTicketBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(ticket: Ticket){
            //Ticket Number
            binding.txvTicketNumber.text = ticket.ticketNumber
            //DateCreation
            binding.txvTicketCreatedDate.text = ticket.createdDate

            //Priority
            binding.chpTicketPriority.text = ticket.priority
            binding.chpTicketPriority.chipBackgroundColor = ColorHelper.ticketPriorityColor(binding.root.context,ticket.priority)

            //Status
            binding.chpTicketStatus.text = ticket.status
            binding.chpTicketStatus.chipBackgroundColor = ColorHelper.ticketStatusColor(binding.root.context,ticket.status)


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketsRecyclerViewAdapter.ViewHolder {
        return ViewHolder(ItemTicketBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: TicketsRecyclerViewAdapter.ViewHolder, position: Int) {
        val ticket = tickets[position]
        holder.bind(ticket)

        holder.itemView.setOnClickListener {
            onTicketClick(ticket)
        }
    }

    override fun getItemCount()= tickets.size

}