package com.example.todayido.home.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todayido.baseAndUtils.RecyclerTouchHelperListener
import com.example.todayido.customView.CardTaskView
import com.example.todayido.home.viewHolder.CardTaskHolder
import com.example.todayido.home.viewHolder.TaskBaseHolder
import com.example.todayido.model.TaskCardData
import com.example.todayido.room.TaskEntity
import java.util.*

class TaskAdapter(
    private var listTaskData: MutableList<TaskEntity>?,
    private val onItemClick: ((Int) -> Unit)? = null
) : RecyclerView.Adapter<TaskBaseHolder>(), RecyclerTouchHelperListener  {

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        Collections.swap(listTaskData, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
        return true
    }

    override fun onItemDismiss(position: Int) {
        listTaskData?.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): TaskBaseHolder {
        return CardTaskHolder(CardTaskView(viewGroup.context))
    }

    override fun getItemCount(): Int {
        return listTaskData?.size ?: 0
    }

    override fun onBindViewHolder(holder: TaskBaseHolder, position: Int) {
        listTaskData?.let {
            val data = it[position]
            holder.bindData(
                data,
                onItemClick,
                position
            )
        }
    }

    fun updateData(listTaskData: MutableList<TaskEntity>?) {
        this.listTaskData = listTaskData
        notifyDataSetChanged()
    }

}