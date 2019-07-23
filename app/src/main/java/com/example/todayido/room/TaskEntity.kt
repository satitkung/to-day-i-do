package com.example.todayido.room

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "task_table")
class TaskEntity(
    var title: String,
    var description: String? = null,
    var position: Int,
    var tagColor: Int? = null,
    var image: String? = null) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}


