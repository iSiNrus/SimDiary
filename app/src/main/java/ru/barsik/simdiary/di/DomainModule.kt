package ru.barsik.simdiary.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.barsik.domain.repository.TaskRepository
import ru.barsik.domain.usecase.*

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    fun provideDeleteTaskUseCase(taskRepository: TaskRepository) : DeleteTaskUseCase{
        return DeleteTaskUseCase(taskRepository)
    }

    @Provides
    fun provideGetTaskByIdUseCase(taskRepository: TaskRepository) : GetTaskByIdUseCase{
        return GetTaskByIdUseCase(taskRepository)
    }

    @Provides
    fun provideGetTasksByDateUseCase(taskRepository: TaskRepository) : GetTasksByDateUseCase{
        return GetTasksByDateUseCase(taskRepository)
    }

    @Provides
    fun provideSaveNewTaskUseCase(taskRepository: TaskRepository) : SaveNewTaskUseCase{
        return SaveNewTaskUseCase(taskRepository)
    }

    @Provides
    fun provideUpdateTaskUseCase(taskRepository: TaskRepository) : UpdateTaskUseCase {
        return UpdateTaskUseCase(taskRepository)
    }

}