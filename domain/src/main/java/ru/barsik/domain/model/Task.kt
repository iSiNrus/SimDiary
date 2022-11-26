package ru.barsik.domain.model

import ru.barsik.domain.getDateInt
import ru.barsik.domain.printDateTime
import java.util.*

data class Task(
    val id: Long = UUID.randomUUID().mostSignificantBits,
    var date_start: Long = 0,
    var date_finish: Long = 0,
    var name: String = "Unknown Task",
    var description: String = "None",
    private val calendarStart: Calendar = Calendar.getInstance(),
    private val calendarFinish: Calendar = Calendar.getInstance()
) {
    init {
        calendarStart.timeInMillis = date_start
        calendarFinish.timeInMillis = date_finish
    }

    override fun toString(): String {
        return "$id# $name $date_start $description"
    }

    fun getHourOfStart(): Int {
        return calendarStart.get(Calendar.HOUR_OF_DAY)
    }

    fun getHourOfFinish(): Int {
        var res = calendarFinish.get(Calendar.HOUR_OF_DAY)
        if (calendarFinish.get(Calendar.MINUTE) == 0 && getHourOfStart() != res) res--
        return res
    }

    fun getDateOfStart(): Int {
        return calendarStart.getDateInt()
    }

    fun getDateOfFinish(): Int {
        return calendarFinish.getDateInt()
    }

    fun dateTimeStartText(): String {
        return calendarStart.printDateTime()
    }

    fun dateTimeFinishText(): String {
        return calendarFinish.printDateTime()
    }
}