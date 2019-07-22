package com.example.todayido.home.viewHolder

import com.example.todayido.customView.CardTaskView
import com.example.todayido.model.TaskCardData
import com.example.todayido.room.TaskEntity


class CardTaskHolder(private val view: CardTaskView): TaskBaseHolder(view) {

    override fun bindData(
        data: TaskEntity,
        onItemClick: ((Int) -> Unit)?,
        position: Int
    ) {
        view.bindData(data, onItemClick, position)
    }

}