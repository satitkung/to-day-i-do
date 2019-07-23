package com.example.todayido.customView

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import com.example.todayido.R
import com.example.todayido.model.TaskCardData
import kotlinx.android.synthetic.main.item_card_task_view.view.*


class CardTaskView
    @JvmOverloads
    constructor(context: Context,
                attrs: AttributeSet? = null,
                defStyleAttr: Int = 0)
    : RelativeLayout(context, attrs, defStyleAttr) {

    private val mContext = context

    init {
        inflate(context, R.layout.item_card_task_view, this)
    }

    fun bindData(
        data: TaskCardData,
        onItemClick: ((Int) -> Unit)?,
        position: Int
    ) {
        tv_title.text = data.title

        data.description?.let {
            tv_description.text = it
        } ?: run {
            tv_description.visibility = View.GONE
        }

        data.image?.let {
            img_description.setImageBitmap(it)
        } ?: run {
            img_description.visibility = View.GONE
        }

        data.tagColor?.let {
            img_tag.setColorFilter(ContextCompat.getColor(mContext, it))
        } ?: run {
            img_tag.visibility = View.GONE
        }

        onItemClick?.let { click ->
            main_layout.setOnClickListener {
                click(position)
            }
        } ?: run {
            main_layout.setOnClickListener(null)
        }
    }
}