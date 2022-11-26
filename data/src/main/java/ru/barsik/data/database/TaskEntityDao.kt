package ru.barsik.data.database

import androidx.room.*

@Dao
interface TaskEntityDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun saveTaskEntity(task: TaskEntity) : Long

    @Query("SELECT * FROM Task WHERE id=:taskId")
    suspend fun getTaskEntityById(taskId: Long): TaskEntity

    @Query("SELECT * FROM Task WHERE NOT (date_finish < :tsBegin OR date_start > :tsEnd)")
    suspend fun getTaskEntityByTimeBounds(tsBegin: Long, tsEnd:Long): List<TaskEntity>

    @Query("DELETE FROM Task WHERE id = :taskId")
    suspend fun deleteTaskEntityById(taskId: Long): Int

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun updateTaskEntity(taskEntity: TaskEntity) : Int
}