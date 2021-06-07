package com.example.facialdetection.retro

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.facialdetection.R
import com.example.facialdetection.databinding.FragmentUploadImageBinding

class UploadImageFragment : Fragment() {

    lateinit var binding:FragmentUploadImageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_upload_image, container, false)
        return binding.root
    }

}