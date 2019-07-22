package com.example.todayido.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_table")
class TaskEntity(
    @PrimaryKey(autoGenerate = true)  var id: Int = 0,
    val title: String,
    val description: String? = null,
    val position: Int,
    val tagColor: Int? = null,
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB) var image: ByteArray? = null)


