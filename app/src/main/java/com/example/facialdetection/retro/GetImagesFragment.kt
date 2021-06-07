package com.example.facialdetection.retro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TableLayout
import android.widget.TableRow
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.facialdetection.Image
import com.example.facialdetection.R
import com.example.facialdetection.databinding.FragmentGetImagesBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetImagesFragment : Fragment() {
    lateinit var table:TableLayout
    lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentGetImagesBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_get_images, container, false)
        table = binding.all
        progressBar = binding.progressBar1
        return binding.root
    }


}

