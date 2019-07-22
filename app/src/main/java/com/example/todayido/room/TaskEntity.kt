package com.example.todayido.room

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "task_table")
class TaskEntity(
    val title: String,
    val description: String? = null,
    val position: Int,
    val tagColor: Int? = null,
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB) var image: ByteArray? = null) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}


