package com.example.todayido.EditCardTask.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProviders
import com.example.todayido.EditCardTask.viewModel.EditCardViewModel
import com.example.todayido.R
import com.example.todayido.baseAndUtils.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_card_task.*


class EditCardTaskActivity : BaseActivity() {

    private lateinit var editCardViewModel: EditCardViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_card_task)
        initViewModel()
    }

    override fun initViewModel() {
        editCardViewModel = ViewModelProviders.of(this).get(EditCardViewModel::class.java)
        setupView()
    }

    private fun setupView() {
        initSpinnerTag()

    }

    private fun initSpinnerTag() {
        val listTags = resources.getStringArray(R.array.tag)
        val adapterTags = ArrayAdapter<String>(
            this,
            android.R.layout.simple_dropdown_item_1line, listTags
        )
        spn_tag.adapter = adapterTags
        spn_tag.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Log.d("tee", listTags[position])
            }
        }
    }
}
