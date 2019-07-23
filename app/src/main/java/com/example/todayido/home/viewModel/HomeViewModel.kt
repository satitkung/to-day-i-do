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
import com.example.todayido.baseAndUtils.Utils
import com.example.todayido.home.activity.HomeActivity
import com.example.todayido.model.TaskCardData
import com.example.todayido.room.TaskEntity


class HomeViewModel(app: Application): AndroidViewModel(app) {

    private val context: Context = getApplication<Application>().applicationContext
    private var tasklists: LiveData<MutableList<TaskEntity>>? = null
    private var tasklist = MutableLiveData<MutableList<TaskEntity>>()
    private var taskCardDataList = mutableListOf<TaskEntity>()
    private val taskRepositoryManager by lazy {
        TaskRepositoryManager(context)
    }

    fun getListTask():  LiveData<MutableList<TaskEntity>>?{
        tasklists =  taskRepositoryManager.getListTasks()
        tasklist.postValue(tasklists?.value)
        return tasklists!!
    }

    fun setListTaskByTags(tag: Int) {
        val data =  taskRepositoryManager.getListTaskByTags(tag)
        tasklist.postValue(data)
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
//            "None",
            "Red",
            "Green",
            "Blue")
        pictureDialog.setItems(pictureDialogItems) { _, which ->
            when (which) {
//                0 -> getListTask()
                0 -> setListTaskByTags(R.color.colorRed)
                1 -> setListTaskByTags(R.color.colorGreen)
                2 -> setListTaskByTags(R.color.colorBlue)
            }
        }
        pictureDialog.show()
    }

    fun convertDataToModel(listTaskEntity: MutableList<TaskEntity>?, activity: Activity): MutableList<TaskCardData> {
        val listData = mutableListOf<TaskCardData>()
        listTaskEntity?.let {
            it.forEach { taskEntity ->
                listData.add(TaskCardData(
                    taskEntity.id,
                    taskEntity.title,
                    taskEntity.description,
                    taskEntity.position,
                    taskEntity.tagColor,
                    Utils.getImageFromUri(activity, taskEntity.image)))
            }
            return listData
        } ?: run {
            return listData
        }
    }
}
