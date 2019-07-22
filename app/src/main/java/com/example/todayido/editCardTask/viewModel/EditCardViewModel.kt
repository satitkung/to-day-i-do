package com.example.todayido.editCardTask.viewModel

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.lifecycle.AndroidViewModel

import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentActivity
import com.example.todayido.room.TaskEntity
import com.tbruyelle.rxpermissions2.RxPermissions

import com.example.todayido.baseAndUtils.TaskRepositoryManager



const val CHOOSE_IMAGE_FROM_GALLERY = 1
const val CHOOSE_IMAGE_FROM_CAMERA = 2
class EditCardViewModel(app: Application): AndroidViewModel(app) {
    private val context: Context = getApplication<Application>().applicationContext
    private val taskRepositoryManager by lazy {
        TaskRepositoryManager(context)
    }

    fun insertTask(taskEntity: TaskEntity) {
        taskRepositoryManager.insertTask(taskEntity)
    }

    fun updateTask(taskEntity: TaskEntity) {
        taskRepositoryManager.updateTask(taskEntity)
    }

    private fun choosePhotoFromGallary(activity: Activity) {
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        startActivityForResult(activity, galleryIntent, CHOOSE_IMAGE_FROM_GALLERY, null)
    }

    @SuppressLint("CheckResult")
    private fun takePhotoFromCamera(activity: Activity) {
        RxPermissions(activity as FragmentActivity).
            request(Manifest.permission.CAMERA).subscribe { granted ->
            if (granted) {
                val intent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(activity, intent, CHOOSE_IMAGE_FROM_CAMERA, null)
            }
        }
    }

    fun showPictureDialog(activity: Activity) {
        val pictureDialog = AlertDialog.Builder(activity)
        pictureDialog.setTitle("Select Action")
        val pictureDialogItems = arrayOf("Select photo from gallery", "Capture photo from camera")
        pictureDialog.setItems(pictureDialogItems) { _, which ->
            when (which) {
                0 -> choosePhotoFromGallary(activity)
                1 -> takePhotoFromCamera(activity)
            }
        }
        pictureDialog.show()
    }

}
