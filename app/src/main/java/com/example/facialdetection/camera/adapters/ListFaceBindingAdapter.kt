package com.example.facialdetection.camera.adapters

import android.graphics.BitmapFactory
import android.net.Uri
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.facialdetection.database.FaceTable

@BindingAdapter("setText")
fun TextView.setText(value: FaceTable){
    text = value.faceName
}

@BindingAdapter("setBitmap")
fun ImageView.setBitmap(data: FaceTable){
    val img = Uri.parse(data.faceUri)
    var bmp =
        BitmapFactory.decodeStream(context?.contentResolver?.openInputStream(img!!))
    setImageBitmap(bmp)
}