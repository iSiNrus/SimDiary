package ru.barsik.domain.usecase

import ru.barsik.domain.model.Task
import ru.barsik.domain.repository.TaskRepository
import java.util.Calendar
import kotlin.collections.ArrayList
import kotlin.random.Random

class GetTasksByDateUseCase(private val taskRepository: TaskRepository) {

    suspend fun execute(day: Int, month: Int, year: Int): List<Task> {
        val tsTodayStart =  1669148400000
        val tsTodayEnd =    1669231200000

        val list = ArrayList<Task>()

//        for (i in 0..15) {
//            val dateStart = (Random.nextLong(tsTodayStart, tsTodayEnd))
//            val dateFinish = (Random.nextLong(dateStart, tsTodayEnd))
//            list.add(
//
//                Task(
//                    description = "descr $i",
//                    name = "task name $i",
//                    date_start = dateStart,
//                    date_finish = dateFinish
//                )
//            )
//        }

        val startDate = Calendar.getInstance()
        with(startDate) {
            set(Calendar.DAY_OF_MONTH, day)
            set(Calendar.MONTH, month)
            set(Calendar.YEAR, year)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val endDate = Calendar.getInstance()
        with(endDate) {
            set(Calendar.DAY_OF_MONTH, day)
            set(Calendar.MONTH, month)
            set(Calendar.YEAR, year)
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
            set(Calendar.MILLISECOND, 999)
        }
        return taskRepository.getTaskByTimeBounds(startDate.timeInMillis, endDate.timeInMillis)
//        return list
    }

}