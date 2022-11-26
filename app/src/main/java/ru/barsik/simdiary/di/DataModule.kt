package ru.barsik.simdiary.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.barsik.data.database.TaskDatabase
import ru.barsik.data.repository.TaskRepositoryImpl
import ru.barsik.domain.repository.TaskRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideTaskDatabase(@ApplicationContext context: Context) : TaskDatabase {
        return Room.databaseBuilder(context, TaskDatabase::class.java, "TaskDatabase").build()
    }

    @Provides
    @Singleton
    fun provideTaskRepository(taskDatabase: TaskDatabase): TaskRepository {
        return TaskRepositoryImpl(taskDatabase = taskDatabase)
    }

}