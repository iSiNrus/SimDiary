package ru.barsik.usecase

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import ru.barsik.domain.model.Task
import ru.barsik.domain.repository.TaskRepository
import ru.barsik.domain.usecase.GetTaskByIdUseCase

class GetTaskByIdUseCaseTest {

    @Test
    fun getTaskByIdTest() = runBlocking {

        val taskRepository = mock<TaskRepository>()
        Mockito.`when`(taskRepository.getTaskById(any()))
            .thenReturn(Task(id = 0, name = "name", description = "descr",
                date_start = 0, date_finish = 0))

        val useCase = GetTaskByIdUseCase(taskRepository)

        val expected = Task(id = 0, name = "name", description = "descr",
            date_start = 0, date_finish = 0)

        val actual = useCase.execute(0)
        Assertions.assertEquals(expected, actual)

    }

}