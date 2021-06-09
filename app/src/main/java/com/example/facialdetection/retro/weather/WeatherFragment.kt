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
import com.bumptech.glide.Glide
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

        // Set the view model
        viewModelFactory = WeatherViewModelFactory(args.query)
        viewModel = ViewModelProvider(this, viewModelFactory).get(WeatherViewModel::class.java)
        viewModel.getWeather()
        binding.weatherView = viewModel
        binding.lifecycleOwner = this

        // observe live data
        viewModel.state.observe(viewLifecycleOwner, Observer { str->
            binding.trial.text = str
        })

        viewModel.date.observe(viewLifecycleOwner, Observer {
            if(it != "") {
                binding.weatherLocationHeaderDetails.visibility = View.VISIBLE
                binding.weatherProgressBar.visibility = View.GONE
            }
        })

        viewModel.picture.observe(viewLifecycleOwner, Observer { icon ->
            Glide.with(requireContext())
                .load(icon)
                .placeholder(R.drawable.image)
                .error(R.drawable.image_not_found)
                .centerCrop()
                .into(binding.weatherImage)
        })



        return binding.root
    }
}