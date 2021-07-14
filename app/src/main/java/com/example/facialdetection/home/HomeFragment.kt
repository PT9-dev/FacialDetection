package com.example.facialdetection.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.facialdetection.MainActivity
import com.example.facialdetection.MainActivityViewModel
import com.example.facialdetection.R
import com.example.facialdetection.databinding.FragmentHomeBinding
import com.example.facialdetection.retro.RetroActivity

class HomeFragment : Fragment() {

    private lateinit var mainActivityViewModel: MainActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        val binding: FragmentHomeBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        mainActivityViewModel = MainActivityViewModel()


        binding.startButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_listFaceFragment)
        }

        binding.viewImaqes.setOnClickListener {
            startRetroActivity()
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        mainActivityViewModel.setTitle("Hieieieie")
    }



    private fun startRetroActivity() {
        startActivity(
            Intent(activity, RetroActivity::class.java)
        )
    }


}