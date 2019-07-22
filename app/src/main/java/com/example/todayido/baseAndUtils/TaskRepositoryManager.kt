package com.example.todayido.baseAndUtils

import android.annotation.SuppressLint
import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.example.todayido.room.TaskDao
import com.example.todayido.room.TaskDatabase
import com.example.todayido.room.TaskEntity


class TaskRepositoryManager(context: Context) {
    private val taskDatabase by lazy {
        TaskDatabase.getInstance(context)
    }

    fun insertTask(taskEntity: TaskEntity) {
        taskDatabase?.taskDao()?.let {
            InsertAsyncTask(it).execute(taskEntity)
        }
    }

    fun getListTasks(): LiveData<MutableList<TaskEntity>>? {
        return  taskDatabase?.taskDao()?.getListTasks()
    }

    fun getListTaskByTags(tag: Int): LiveData<MutableList<TaskEntity>>? {
        return  taskDatabase?.taskDao()?.getListTaskByTags(tag)
    }

    fun deleteTask(taskEntity: TaskEntity) {
        taskDatabase?.taskDao()?.let {
            DeleteAsyncTask(it).execute(taskEntity)
        }    }

    fun updateTask(taskEntity: TaskEntity) {
        taskDatabase?.taskDao()?.let {
            UpdateAsyncTask(it).execute(taskEntity)
        }    }

    @SuppressLint("StaticFieldLeak")
    private open inner class OperationsAsyncTask internal constructor(internal var mAsyncTaskDao: TaskDao) :
        AsyncTask<TaskEntity, Void, Void>() {
        override fun doInBackground(vararg taskEntity: TaskEntity): Void? {
            return null
        }
    }

    @SuppressLint("StaticFieldLeak")
    private inner class InsertAsyncTask internal constructor(taskDao: TaskDao) : OperationsAsyncTask(taskDao) {
        override fun doInBackground(vararg taskEntity: TaskEntity): Void? {
            mAsyncTaskDao.insert(taskEntity[0])
            return null
        }
    }

    @SuppressLint("StaticFieldLeak")
    private inner class DeleteAsyncTask internal constructor(taskDao: TaskDao) : OperationsAsyncTask(taskDao) {
        override fun doInBackground(vararg taskEntity: TaskEntity): Void? {
            mAsyncTaskDao.deleteTask(taskEntity[0])
            return null
        }
    }

    @SuppressLint("StaticFieldLeak")
    private inner class UpdateAsyncTask internal constructor(taskDao: TaskDao) : OperationsAsyncTask(taskDao) {
        override fun doInBackground(vararg taskEntity: TaskEntity): Void? {
            mAsyncTaskDao.updateTask(taskEntity[0])
            return null
        }
    }
}