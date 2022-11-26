package ru.barsik.data.mappers

import ru.barsik.data.database.TaskEntity
import ru.barsik.domain.model.Task


    fun Task.toTaskEntity(): TaskEntity  {
        return TaskEntity(
            id = this.id,
            name = this.name,
            description = this.description,
            date_start = this.date_start,
            date_finish = this.date_finish
        )
    }
    fun TaskEntity.toTask(): Task {
        return Task(
            id = this.id,
            name = this.name,
            description = this.description,
            date_start = this.date_start,
            date_finish = this.date_finish
        )
    }