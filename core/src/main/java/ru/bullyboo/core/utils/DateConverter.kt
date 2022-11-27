package ru.bullyboo.core.utils

import ru.bullyboo.core.enums.DateMask
import java.text.SimpleDateFormat
import java.util.*

object DateConverter {

    fun parse(date: String, mask: DateMask = DateMask.YYYY_MM_DD_T_HH_MM_SS_TIMEZONE): Long {
        if (date.isEmpty()) {
            return 0L
        }
        val format = SimpleDateFormat(mask.source, Locale.getDefault())
        return format.parse(date)!!.time
    }

    fun parse(time: Long, mask: DateMask = DateMask.YYYY_MM_DD_T_HH_MM_SS): String {
        val format = SimpleDateFormat(mask.source, Locale.getDefault())
        return format.format(Date(time))
    }

    fun parse(
        calendar: Calendar, mask: DateMask = DateMask.YYYY_MM_DD_T_HH_MM_SS_TIMEZONE
    ): String {
        return parse(calendar.timeInMillis, mask)
    }
}