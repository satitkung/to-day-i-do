package com.example.todayido.home.viewModel

import android.app.Application
import android.content.Context
import android.content.Entity
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.todayido.baseAndUtils.TaskRepositoryManager
import com.example.todayido.home.activity.HomeActivity
import com.example.todayido.room.TaskEntity


class HomeViewModel(app: Application): AndroidViewModel(app) {

    private val context: Context = getApplication<Application>().applicationContext
    private var tasklists: LiveData<MutableList<TaskEntity>>? = null
    private val taskRepositoryManager by lazy {
        TaskRepositoryManager(context)
    }

    fun getListTask():  LiveData<MutableList<TaskEntity>>?{
        tasklists =  taskRepositoryManager.getListTasks()
        return tasklists!!
    }

    fun getListTaskByTags(tag: String): LiveData<MutableList<TaskEntity>> {
        tasklists =  taskRepositoryManager.getListTaskByTags(tag)
        return tasklists!!
    }

    fun insertTask(taskEntity: TaskEntity) {
        taskRepositoryManager.insertTask(taskEntity)
    }

    fun deleteTask(taskEntity: TaskEntity) {
        taskRepositoryManager.deleteTask(taskEntity)
    }

    fun updateTask(taskEntity: TaskEntity) {
        taskRepositoryManager.updateTask(taskEntity)
    }


}
