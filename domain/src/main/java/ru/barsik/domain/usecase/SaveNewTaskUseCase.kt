package ru.barsik.domain.usecase

import ru.barsik.domain.model.Task
import ru.barsik.domain.repository.TaskRepository

class SaveNewTaskUseCase(private val taskRepository: TaskRepository) {

    suspend fun execute(task: Task): Boolean {
        val resId = taskRepository.saveTask(task)
        return resId != 0L
    }
}