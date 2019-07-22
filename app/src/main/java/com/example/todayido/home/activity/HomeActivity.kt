package com.example.todayido.home.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.annotation.NonNull
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todayido.editCardTask.activity.EditCardTaskActivity
import com.example.todayido.R
import com.example.todayido.baseAndUtils.BaseActivity
import com.example.todayido.baseAndUtils.RecyclerTouchHelperCallback
import com.example.todayido.home.adapter.TaskAdapter
import com.example.todayido.home.viewModel.HomeViewModel

import com.example.todayido.room.TaskEntity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.buttom_sheet_layout.view.*

class HomeActivity : BaseActivity() {

    private var cardSelectPosition = 0
    private var listTask: MutableList<TaskEntity>? = null
    private lateinit var adapter: TaskAdapter
    private lateinit var homeViewModel: HomeViewModel
    private var bottomSheetCallback: BottomSheetBehavior.BottomSheetCallback =
        object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(@NonNull bottomSheet: View, newState: Int) {

            }

            override fun onSlide(@NonNull bottomSheet: View, slideOffset: Float) {

            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViewModel()
    }

    override fun initViewModel() {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        setupView()
    }

    private fun setupView() {
        setToolbar()
        //set buttom sheet
        val bottomSheetView = layoutInflater.inflate(R.layout.buttom_sheet_layout, null)
        val bottomSheetDialog = BottomSheetDialog(this@HomeActivity)
        bottomSheetDialog.setContentView(bottomSheetView)
        val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetView.parent as View)
        bottomSheetBehavior.setBottomSheetCallback(bottomSheetCallback)
        bottomSheetView.menu_bottom_sheet_edit.setOnClickListener {
            editCardTaskScreen()
            bottomSheetDialog.hide()
        }
        bottomSheetView.menu_bottom_sheet_delete.setOnClickListener {
            deleteCardTask()
            bottomSheetDialog.hide()
        }

        //set recyclerview
        rcv_list_task.layoutManager = LinearLayoutManager(this)
        adapter = TaskAdapter(
            listTask,
            onItemClick = {
                cardSelectPosition = it
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                bottomSheetDialog.show()
            },
            onSwipeItem = {
                cardSelectPosition = it
                deleteCardTask()
            }
        )

        homeViewModel.getListTaskByFilterTags().observe(this, Observer { list ->
            adapter.updateData(list)
        })

        homeViewModel.getListTask()?.observe(this, Observer { list ->
            listTask = list
            adapter.updateData(list)
        })

        rcv_list_task.adapter = adapter
        val callback = RecyclerTouchHelperCallback(adapter)
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rcv_list_task)

        //set fab button
        fab_create.setOnClickListener {
            createCardTaskScreen()
        }
    }

    private fun editCardTaskScreen() {
        listTask?.let {
            Intent(this@HomeActivity, EditCardTaskActivity::class.java).apply {
                this.putExtra("task_object", it[cardSelectPosition])
            }.run {
                startActivity(this)
            }
        }
    }

    private fun createCardTaskScreen() {
        Intent(this@HomeActivity, EditCardTaskActivity::class.java).apply {
            this.putExtra("list_task_size", listTask?.size)
        }.run {
            startActivity(this)
        }
    }

    private fun deleteCardTask() {
        listTask?.let {
            if (it.isNotEmpty())
                homeViewModel.deleteTask(it[cardSelectPosition])
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.filter, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_filter -> {
                homeViewModel.showPictureDialog(this@HomeActivity)
            }
            else -> {
                // Do nothing
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
