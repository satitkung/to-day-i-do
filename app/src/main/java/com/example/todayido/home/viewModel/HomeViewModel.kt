package com.example.todayido.home.viewModel

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Entity
import android.content.Intent
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.todayido.R
import com.example.todayido.baseAndUtils.TaskRepositoryManager
import com.example.todayido.home.activity.HomeActivity
import com.example.todayido.room.TaskEntity


class HomeViewModel(app: Application): AndroidViewModel(app) {

    private val context: Context = getApplication<Application>().applicationContext
    private var tasklists: LiveData<MutableList<TaskEntity>>? = null
    private var tasklist = MutableLiveData<MutableList<TaskEntity>>()
    private val taskRepositoryManager by lazy {
        TaskRepositoryManager(context)
    }

    fun getListTask():  LiveData<MutableList<TaskEntity>>?{
        tasklists =  taskRepositoryManager.getListTasks()
        return tasklists!!
    }

    fun getListTaskByTags(tag: Int): LiveData<MutableList<TaskEntity>> {
        tasklists =  taskRepositoryManager.getListTaskByTags(tag)
        tasklist.value = tasklists?.value
        return tasklists!!
    }

    fun getListTaskByFilterTags(): MutableLiveData<MutableList<TaskEntity>> {
        return tasklist
    }

    fun deleteTask(taskEntity: TaskEntity) {
        taskRepositoryManager.deleteTask(taskEntity)
    }

    fun showPictureDialog(activity: Activity) {
        val pictureDialog = AlertDialog.Builder(activity)
        pictureDialog.setTitle("Filter by")
        val pictureDialogItems = arrayOf(
            "None",
            "Red",
            "Green",
            "Blue")
        pictureDialog.setItems(pictureDialogItems) { _, which ->
            when (which) {
                0 -> getListTask()
                1 -> getListTaskByTags(R.color.colorRed)
                2 -> getListTaskByTags(R.color.colorGreen)
                3 -> getListTaskByTags(R.color.colorBlue)
            }
        }
        pictureDialog.show()
    }
}
