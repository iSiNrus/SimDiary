package ru.barsik.domain.usecase

import ru.barsik.domain.model.Task
import ru.barsik.domain.repository.TaskRepository

class DeleteTaskUseCase(private val taskRepository: TaskRepository) {
    suspend fun execute(task: Task): Boolean {
        val res = taskRepository.deleteTask(task)
        return res != 0
    }
}