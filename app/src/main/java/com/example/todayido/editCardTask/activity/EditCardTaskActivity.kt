package com.example.todayido.editCardTask.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProviders
import com.example.todayido.editCardTask.viewModel.EditCardViewModel
import com.example.todayido.R
import com.example.todayido.baseAndUtils.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_card_task.*
import android.provider.MediaStore
import android.app.Activity
import android.content.Intent
import com.example.todayido.editCardTask.viewModel.CHOOSE_IMAGE_FROM_GALLERY
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import com.example.todayido.baseAndUtils.Utils
import com.example.todayido.editCardTask.viewModel.CHOOSE_IMAGE_FROM_CAMERA
import com.example.todayido.model.Tags
import com.example.todayido.room.TaskEntity
import java.io.ByteArrayOutputStream


class EditCardTaskActivity : BaseActivity() {

    private lateinit var editCardViewModel: EditCardViewModel
    private var optionMenu: Menu? = null
    private var tagSelected: Int? = null
    private var listTaskSize = 0
    private var taskEntity: TaskEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_card_task)
        initViewModel()
    }

    override fun initViewModel() {
        editCardViewModel = ViewModelProviders.of(this).get(EditCardViewModel::class.java)
        listTaskSize = intent?.getIntExtra("list_task_size", 0) ?: 0
//        taskEntity = intent?.extras?.getParcelable("task_object")
        setupView()
    }

    private fun setupView() {
        setToolbar()
        setUpData()
        initSpinnerTag()
        img_camera.setOnClickListener {
            editCardViewModel.showPictureDialog(this@EditCardTaskActivity)
        }

        tv_delete_place.setOnClickListener {
            clearImage()
        }

        with(com.example.todayido.baseAndUtils.TextWatcher) {
            edt_title.afterTextChanged {
                optionMenu?.getItem(0)?.isEnabled = !it.isBlank()
            }
        }
    }

    private fun setUpData() {
        taskEntity?.let {
            edt_title.setText(it.title)
            edt_description.setText(it.description ?: "")
            it.tagColor?.let { color ->
                spn_tag.setSelection(getSelectionTag(color))
            }
            it.image?.let{ image ->
//                img_place.setImageBitmap(Utils.imageByteArrayToBitmap(image))
            }
        }
    }

    private fun getSelectionTag(color: Int): Int {
        return when (color) {
            R.color.colorRed -> 1
            R.color.colorGreen -> 2
            R.color.colorBlue -> 3
            else -> 0
        }
    }

    private fun clearImage() {
        tv_delete_place.visibility = View.GONE
        img_place.setImageBitmap(null)
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
                tagSelected = when (listTags[position]) {
                    Tags.Blue.name -> R.color.colorBlue
                    Tags.Red.name -> R.color.colorRed
                    Tags.Green.name -> R.color.colorGreen
                    else -> {
                        null
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK)
            when (requestCode) {
                CHOOSE_IMAGE_FROM_GALLERY -> {
                    data?.let {
                        val contentURI = it.data
                        contentURI?.let { uri ->
                            editCardViewModel.setPictureUri(uri)
                            val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, contentURI)
                            setImagePlace(bitmap)
                        }
                    }
                }

                CHOOSE_IMAGE_FROM_CAMERA -> {
                    val thumbnail = data?.extras?.get("data") as? Bitmap
                    thumbnail?.let {
                        setImagePlace(it)
                    }
                }
            }
    }

    private fun setImagePlace(bitmap: Bitmap) {
        img_place.setImageBitmap(bitmap)
        tv_delete_place.visibility = View.VISIBLE
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        optionMenu = menu
        val inflater = menuInflater
        inflater.inflate(R.menu.save, menu)
        optionMenu?.getItem(0)?.isEnabled = false
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_save -> {
                optionMenu?.getItem(0)?.isEnabled = false
                editCardViewModel.insertTask(
                    TaskEntity(
                        edt_title.text.toString(),
                        edt_description.text.toString(),
                        listTaskSize + 1,
                        tagSelected,
                        Utils.validateImageNull(img_place, editCardViewModel.getPictureUri())
                ))
                finish()
            }
            else -> {
                // Do nothing
            }
        }
        return super.onOptionsItemSelected(item)
    }



}
