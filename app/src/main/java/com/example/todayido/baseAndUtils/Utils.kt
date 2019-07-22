package com.example.todayido.baseAndUtils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.widget.ImageView
import java.io.ByteArrayOutputStream

object Utils {

    fun imageBitmapToByteArray(image: ImageView): ByteArray? {
        return if (hasImage(image)) {
            val bitmap = (image.drawable as BitmapDrawable).bitmap
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)
            stream.toByteArray()
        } else {
            null
        }
    }

    fun hasImage(view: ImageView): Boolean {
        val drawable = view.drawable
        var hasImage = drawable != null

        if (hasImage && drawable is BitmapDrawable) {
            hasImage = drawable.bitmap != null
        }

        return hasImage
    }

    fun imageByteArrayToBitmap(byteArray: ByteArray): Bitmap? {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }
}