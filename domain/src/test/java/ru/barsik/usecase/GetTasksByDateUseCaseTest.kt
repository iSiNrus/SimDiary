package ru.barsik.usecase

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import ru.barsik.domain.model.Task
import ru.barsik.domain.repository.TaskRepository
import ru.barsik.domain.usecase.GetTasksByDateUseCase

class GetTasksByDateUseCaseTest {

    @Test
    fun getTasksByDateTest()  = runBlocking {

        val taskRepository = mock<TaskRepository>()

        Mockito.`when`(taskRepository.getTaskByTimeBounds(any(), any()))
            .thenReturn(ArrayList<Task>().apply {
                add(
                    Task(
                        id = 1,
                        date_start = 123,
                        date_finish = 167,
                        name = "TestTask",
                        description = "TestDescr"
                    )
                )
            })

        val useCase = GetTasksByDateUseCase(taskRepository)

        val expected = ArrayList<Task>().apply {
            add(
                Task(
                    id = 1,
                    date_start = 123,
                    date_finish = 167,
                    name = "TestTask",
                    description = "TestDescr"
                )
            )
        }

        val actual = useCase.execute(1, 2, 3)
        Assertions.assertEquals(expected, actual)
    }

}