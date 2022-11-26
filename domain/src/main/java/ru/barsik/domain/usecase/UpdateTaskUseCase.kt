package ru.barsik.domain.usecase

import ru.barsik.domain.model.Task
import ru.barsik.domain.repository.TaskRepository

class UpdateTaskUseCase(private val taskRepository: TaskRepository) {

    suspend fun execute(task: Task): Boolean {
        val resCount = taskRepository.updateTask(task)
        return resCount != 0
    }

}