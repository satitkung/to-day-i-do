package com.example.todayido.editCardTask.viewModel

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.lifecycle.AndroidViewModel

import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentActivity
import com.example.todayido.room.TaskEntity
import com.tbruyelle.rxpermissions2.RxPermissions

import com.example.todayido.baseAndUtils.TaskRepositoryManager
import android.os.Environment.getExternalStorageDirectory
import java.io.File


const val CHOOSE_IMAGE_FROM_GALLERY = 1
const val CHOOSE_IMAGE_FROM_CAMERA = 2

class EditCardViewModel(app: Application) : AndroidViewModel(app) {
    private val context: Context = getApplication<Application>().applicationContext
    private val taskRepositoryManager by lazy {
        TaskRepositoryManager(context)
    }
    private var uri: Uri? = null

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

    private fun takePhotoFromCamera(activity: Activity) {
        val intent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
        uri = Uri.fromFile(
            File(
                getExternalStorageDirectory(),
                "pic_" + System.currentTimeMillis().toString() + ".jpg"
            )
        )
        startActivityForResult(activity, intent, CHOOSE_IMAGE_FROM_CAMERA, null)
    }

    fun getPictureUri(): Uri? {
        return uri
    }

    fun setPictureUri(uri: Uri) {
        this.uri = uri
    }

    @SuppressLint("CheckResult")
    fun showPictureDialog(activity: Activity) {
        RxPermissions(activity as FragmentActivity).request(
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ).subscribe { granted ->
            if (granted) {
                val pictureDialog = AlertDialog.Builder(activity)
                pictureDialog.setTitle("Select Action")
                val pictureDialogItems = arrayOf("Select photo from gallery")
                pictureDialog.setItems(pictureDialogItems) { _, which ->
                    when (which) {
                        0 -> choosePhotoFromGallary(activity)
//                        1 -> takePhotoFromCamera(activity)
                    }
                }
                pictureDialog.show()
            }
        }
    }
}
