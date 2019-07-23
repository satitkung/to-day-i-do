package com.example.todayido.room


import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface TaskDao {

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: TaskEntity)

    @Query("SELECT * FROM task_table")
    fun getListTasks(): LiveData<MutableList<TaskEntity>>

    @Query("SELECT * FROM task_table WHERE tagColor = :tag")
    fun getListTaskByTags(tag: Int): MutableList<TaskEntity>

    @Delete
    fun deleteTask(taskEntity: TaskEntity)

    @Update
    fun updateTask(taskEntity: TaskEntity)

}