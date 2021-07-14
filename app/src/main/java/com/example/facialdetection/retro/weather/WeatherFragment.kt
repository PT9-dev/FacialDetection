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
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinApiExtension


@KoinApiExtension
class WeatherFragment : Fragment() {

    private lateinit var binding: FragmentWeatherBinding
    private val weatherViewModel: WeatherViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_weather, container, false)
        binding.weatherView = weatherViewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args: WeatherFragmentArgs by navArgs()
        weatherViewModel.init(args.query)
        weatherViewModel.getWeather()


        // observe live data
        weatherViewModel.state.observe(viewLifecycleOwner, Observer { str->
            binding.trial.text = str
        })

        weatherViewModel.date.observe(viewLifecycleOwner, Observer {
            if(it != "") {
                binding.weatherLocationHeaderDetails.visibility = View.VISIBLE
                binding.weatherProgressBar.visibility = View.GONE
            }
        })

        weatherViewModel.picture.observe(viewLifecycleOwner, Observer { icon ->
            Glide.with(requireContext())
                .load(icon)
                .placeholder(R.drawable.image)
                .error(R.drawable.image_not_found)
                .centerCrop()
                .into(binding.weatherImage)
        })
    }
}