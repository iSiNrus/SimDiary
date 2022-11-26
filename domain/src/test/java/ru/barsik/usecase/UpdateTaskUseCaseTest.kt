package ru.barsik.usecase

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock
import ru.barsik.domain.model.Task
import ru.barsik.domain.repository.TaskRepository
import ru.barsik.domain.usecase.SaveNewTaskUseCase
import ru.barsik.domain.usecase.UpdateTaskUseCase

class UpdateTaskUseCaseTest {

    @Test
    fun updateTaskTest() = runBlocking{
        val taskRepository = mock<TaskRepository>()

        val task =  Task(id = 0, name = "name", description = "descr",
            date_start = 0, date_finish = 0)

        Mockito.`when`(taskRepository.updateTask(task))
            .thenReturn(1)

        val useCase = UpdateTaskUseCase(taskRepository)

        val expected = true

        val actual = useCase.execute(task)
        Assertions.assertEquals(expected, actual)
    }

}