package ru.barsik.data.storage

import ru.barsik.domain.model.Task

class DatabaseTaskStorage : TaskStorage {
    override fun saveTask(task: Task): Boolean {
        TODO("Not yet implemented")
    }

    override fun getTaskById(taskId: Long): Task {
        TODO("Not yet implemented")
    }

    override fun getTaskByTimeBounds(tsBegin: Long, tsEnd: Long): List<Task> {
        TODO("Not yet implemented")
    }

    override fun deleteTaskById(taskId: Long): Boolean {
        TODO("Not yet implemented")
    }

    override fun updateTask(task: Task): Boolean {
        TODO("Not yet implemented")
    }

}