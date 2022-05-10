package ca.qc.cstj.tp2.core

import android.content.Context
import android.content.res.ColorStateList
import androidx.core.content.ContextCompat
import ca.qc.cstj.tp2.R

//TODO: remove comments when R.color. ticket are done

object ColorHelper {
    fun ticketPriorityColor(context: Context, priority: String): ColorStateList {
        val color = when (Constants.TicketPriority.valueOf(priority)) {
            Constants.TicketPriority.Low -> R.color.ticket_priority_low
            Constants.TicketPriority.Normal -> R.color.ticket_priority_normal
            Constants.TicketPriority.High -> R.color.ticket_priority_high
            Constants.TicketPriority.Critical -> R.color.ticket_priority_critical
        }
        return ContextCompat.getColorStateList(context, color)!!
    }

    fun connectionStatusColor(context: Context, status: String): ColorStateList {
        val color = when (Constants.ConnectionStatus.valueOf(status)) {
            Constants.ConnectionStatus.Online -> R.color.gateway_status_online
            Constants.ConnectionStatus.Offline -> R.color.gateway_status_offline
        }
        return ContextCompat.getColorStateList(context, color)!!
    }

    fun ticketStatusColor(context: Context, status: String): ColorStateList {
        val color = when (Constants.TicketStatus.valueOf(status)) {
            Constants.TicketStatus.Open -> R.color.ticket_status_open
            Constants.TicketStatus.Solved -> R.color.ticket_status_solved
        }
        return ContextCompat.getColorStateList(context, color)!!
    }
}