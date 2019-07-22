package com.example.todayido.model

import android.graphics.Bitmap

data class TaskCardData(
    val id: Int,
    var title: String,
    var description: String?,
    var image: Bitmap?,
    var position: Int,
    var tagColor: Int?
)