package com.example.todayido.baseAndUtils

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.todayido.room.TaskDatabase
import com.example.todayido.room.TaskEntity


class TaskRepositoryManager(context: Context) {
    private val taskDatabase by lazy {
        TaskDatabase.getInstance(context)
    }

    fun insertTask(taskEntity: TaskEntity) {
        taskDatabase?.taskDao()?.insert(taskEntity)
    }

    fun getListTasks(): LiveData<MutableList<TaskEntity>>? {
        return  taskDatabase?.taskDao()?.getListTasks()
    }

    fun getListTaskByTags(tag: String): LiveData<MutableList<TaskEntity>>? {
        return  taskDatabase?.taskDao()?.getListTaskByTags(tag)
    }

    fun deleteTask(taskEntity: TaskEntity) {
        taskDatabase?.taskDao()?.deleteTask(taskEntity)
    }

    fun updateTask(taskEntity: TaskEntity) {
        taskDatabase?.taskDao()?.updateTask(taskEntity)
    }
}