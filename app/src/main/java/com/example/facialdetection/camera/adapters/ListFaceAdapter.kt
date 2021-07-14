package com.example.facialdetection.camera.adapters

import android.graphics.BitmapFactory
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.facialdetection.R
import com.example.facialdetection.camera.ListFaceViewModel
import com.example.facialdetection.database.FaceTable
import com.example.facialdetection.databinding.ListFaceViewBinding

class ListFaceAdapter : RecyclerView.Adapter<ListFaceAdapter.ViewHolder>() {
    var data = listOf<FaceTable>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder.from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = data[position]
        holder.bind(data)
    }


    class ViewHolder private constructor(val binding: ListFaceViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: FaceTable) {
            binding.face = data
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding: ListFaceViewBinding =
                    DataBindingUtil.inflate(layoutInflater, R.layout.list_face_view, parent, false)
                return ViewHolder(binding)
            }
        }

    }
}