package com.example.todayido.home.viewHolder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.todayido.model.TaskCardData
import com.example.todayido.room.TaskEntity

abstract class TaskBaseHolder(view: View): RecyclerView.ViewHolder(view) {

    abstract fun bindData(
        data: TaskEntity,
        onItemClick: ((Int) -> Unit)?,
        position: Int
    )

}
