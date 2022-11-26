package ru.barsik.domain.usecase

import ru.barsik.domain.model.Task
import ru.barsik.domain.repository.TaskRepository

class GetTaskByIdUseCase(private val taskRepository: TaskRepository) {
    suspend fun execute(taskID : Long) : Task {
        return taskRepository.getTaskById(taskID)
    }
}