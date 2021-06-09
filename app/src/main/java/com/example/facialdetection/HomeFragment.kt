package com.example.facialdetection

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.facialdetection.databinding.FragmentHomeBinding
import com.example.facialdetection.retro.RetroActivity

class HomeFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        val binding: FragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)


        binding.startButton.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_cameraFragment)
        }

        binding.viewImaqes.setOnClickListener{
            startRetroActivity()
        }

        return binding.root
    }

    private fun startRetroActivity(){
        startActivity(
            Intent(activity, RetroActivity::class.java)
        )
    }


}