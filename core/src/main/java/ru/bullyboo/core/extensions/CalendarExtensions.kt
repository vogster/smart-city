package ru.bullyboo.core.extensions

import java.util.*

fun Calendar.isPast(): Boolean {
    return System.currentTimeMillis() > timeInMillis
}

fun Calendar.isFuture(): Boolean {
    return System.currentTimeMillis() < timeInMillis
}

fun Calendar.isToday(): Boolean {
    val currentCalendar = Calendar.getInstance()

    return currentCalendar.get(Calendar.DAY_OF_MONTH) == get(Calendar.DAY_OF_MONTH) &&
        currentCalendar.get(Calendar.MONTH) == get(Calendar.MONTH) &&
        currentCalendar.get(Calendar.YEAR) == get(Calendar.YEAR)
}

fun Calendar.isBetween(
    from: Calendar,
    to: Calendar
): Boolean {
    return from.timeInMillis <= timeInMillis && to.timeInMillis >= timeInMillis
}

fun Calendar.isCurrentYear(): Boolean {
    val currentYear = Calendar.getInstance().get(Calendar.YEAR)

    return get(Calendar.YEAR) == currentYear
}