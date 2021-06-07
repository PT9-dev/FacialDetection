package com.example.facialdetection.retro

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.facialdetection.R
import com.example.facialdetection.databinding.FragmentRetroHomeBinding


class RetroHomeFragment : Fragment() {
    lateinit var binding: FragmentRetroHomeBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_retro_home, container, false)

        binding.uploadImageBtn.setOnClickListener{
            findNavController().navigate(R.id.action_retroHomeFragment_to_uploadImageFragment)
        }

        return binding.root
    }


}