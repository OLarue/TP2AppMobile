package ca.qc.cstj.tp2.core

import java.text.SimpleDateFormat
import java.util.*

object DateHelper {

    fun formatISODate(dateStringIso: String): String {
        val isoDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        isoDateFormat.timeZone = TimeZone.getTimeZone("GMT")

        val dateIso = isoDateFormat.parse(dateStringIso)!!
        return SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(dateIso)
    }
}