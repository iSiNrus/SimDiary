package ru.barsik.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "Task")
data class TaskEntity(
    @PrimaryKey
    val id : Long = UUID.randomUUID().mostSignificantBits,
    var date_start : Long = 0,
    var date_finish : Long = 0,
    var name : String = "Unknown Task",
    var description : String = "None"
)