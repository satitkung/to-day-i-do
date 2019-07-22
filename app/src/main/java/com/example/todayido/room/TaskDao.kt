package com.example.todayido.room


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete
import androidx.room.Update




@Dao
interface TaskDao {

    @Insert
    fun insert(data: TaskEntity)

    @Query("SELECT * FROM task_table")
    fun getListTasks(): LiveData<MutableList<TaskEntity>>

    @Query("SELECT * FROM task_table WHERE tagColor = :tag")
    fun getListTaskByTags(tag: String): LiveData<MutableList<TaskEntity>>

    @Delete
    fun deleteTask(taskEntity: TaskEntity)

    @Update
    fun updateTask(taskEntity: TaskEntity)

}