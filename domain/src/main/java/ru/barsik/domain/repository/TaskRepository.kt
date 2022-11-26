package ru.barsik.domain.repository

import ru.barsik.domain.model.Task

interface TaskRepository {

    suspend fun saveTask(task: Task) : Long

    suspend fun getTaskById(taskId: Long): Task

    suspend fun getTaskByTimeBounds(tsBegin: Long, tsEnd:Long): List<Task>

    suspend fun deleteTask(task: Task): Int

    suspend fun updateTask(task: Task) : Int
}