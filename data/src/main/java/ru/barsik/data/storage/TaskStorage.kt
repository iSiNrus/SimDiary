package ru.barsik.data.storage

import ru.barsik.domain.model.Task

interface TaskStorage {

    fun saveTask(task: Task) : Boolean

    fun getTaskById(taskId: Long): Task

    fun getTaskByTimeBounds(tsBegin: Long, tsEnd:Long): List<Task>

    fun deleteTaskById(taskId: Long): Boolean

    fun updateTask(task: Task) : Boolean

}