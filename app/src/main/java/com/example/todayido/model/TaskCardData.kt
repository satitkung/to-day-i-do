package com.example.todayido.model

import android.graphics.Bitmap
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TaskCardData(
    var id: Int,
    val title: String,
    val description: String? = null,
    val position: Int,
    val tagColor: Int? = null,
    var image: Bitmap? = null) : Parcelable