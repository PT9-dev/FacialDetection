package com.example.facialdetection.retro

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import com.example.facialdetection.R
import com.example.facialdetection.databinding.FragmentWeatherBinding

class WeatherFragment : Fragment() {

    private lateinit var binding: FragmentWeatherBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_weather, container, false)
        val args: WeatherFragmentArgs by navArgs()
        binding.trial.text = args.query
        return binding.root
    }


}