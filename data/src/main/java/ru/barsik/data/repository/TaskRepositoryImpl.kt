package ru.barsik.data.repository

import ru.barsik.data.database.TaskDatabase
import ru.barsik.data.mappers.toTask
import ru.barsik.data.mappers.toTaskEntity
import ru.barsik.domain.model.Task
import ru.barsik.domain.repository.TaskRepository

class TaskRepositoryImpl(private val taskDatabase: TaskDatabase) : TaskRepository {

    override suspend fun saveTask(task: Task): Long {
        return taskDatabase.getTaskDao().saveTaskEntity(task.toTaskEntity())
    }

    override suspend fun getTaskById(taskId: Long): Task {
        return taskDatabase.getTaskDao().getTaskEntityById(taskId).toTask()
    }

    override suspend fun getTaskByTimeBounds(tsBegin: Long, tsEnd: Long): List<Task> {
        return taskDatabase.getTaskDao().getTaskEntityByTimeBounds(tsBegin, tsEnd).map{it.toTask()}
    }

    override suspend fun deleteTask(task: Task): Int {
        return taskDatabase.getTaskDao().deleteTaskEntityById(task.toTaskEntity().id)
    }

    override suspend fun updateTask(task: Task): Int {
        return taskDatabase.getTaskDao().updateTaskEntity(task.toTaskEntity())
    }


}