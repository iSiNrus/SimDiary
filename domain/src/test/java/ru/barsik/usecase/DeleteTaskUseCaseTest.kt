package ru.barsik.usecase

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import ru.barsik.domain.model.Task
import ru.barsik.domain.repository.TaskRepository
import ru.barsik.domain.usecase.DeleteTaskUseCase
import ru.barsik.domain.usecase.GetTaskByIdUseCase

class DeleteTaskUseCaseTest {

    @Test
    fun deleteTaskTest() = runBlocking {

        val task = Task(id = 0, name = "name", description = "descr",
            date_start = 0, date_finish = 0)
        val taskRepository = mock<TaskRepository>()
        Mockito.`when`(taskRepository.deleteTask(task))
            .thenReturn(1)


        val useCase = DeleteTaskUseCase(taskRepository)
        val expected = true

        val actual = useCase.execute(task)
        Assertions.assertEquals(expected, actual)

    }
}