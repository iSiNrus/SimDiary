package ru.barsik.domain

import java.util.*

fun Calendar.printDate(): String {
    return "${this.get(Calendar.DAY_OF_MONTH)} / ${this.get(Calendar.MONTH) + 1} / ${
        this.get(
            Calendar.YEAR
        )
    }"
}

fun Calendar.printDateTime(): String {
    return "${printDate()} ${this.get(Calendar.HOUR_OF_DAY)}:${this.get(Calendar.MINUTE)}"
}

fun Calendar.getDateInt(): Int {
    return "${this.get(Calendar.YEAR)}${this.get(Calendar.MONTH) - 1}${
        this.get(Calendar.DAY_OF_MONTH)
    }".toInt()
}