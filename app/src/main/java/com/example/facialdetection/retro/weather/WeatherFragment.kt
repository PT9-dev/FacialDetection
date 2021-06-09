package com.example.facialdetection.retro.weather

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.facialdetection.R
import com.example.facialdetection.databinding.FragmentWeatherBinding


class WeatherFragment : Fragment() {

    private lateinit var binding: FragmentWeatherBinding
    private lateinit var viewModel: WeatherViewModel
    private lateinit var viewModelFactory: WeatherViewModelFactory


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_weather, container, false)
        val args: WeatherFragmentArgs by navArgs()
        viewModelFactory = WeatherViewModelFactory(args.query)
        viewModel = ViewModelProvider(this, viewModelFactory).get(WeatherViewModel::class.java)
        viewModel.getWeather()
        viewModel.state.observe(viewLifecycleOwner, Observer { str->
            binding.trial.text = str
        })

        return binding.root
    }
}