package com.example.facialdetection.retro

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.facialdetection.R
import com.example.facialdetection.databinding.FragmentRetroHomeBinding
import com.example.facialdetection.retro.pojo.WeatherId
import com.example.facialdetection.retro.weather.api.ApiHelper
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinApiExtension
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@KoinApiExtension
class RetroHomeFragment : Fragment() {
    lateinit var binding: FragmentRetroHomeBinding
    private lateinit var textAdapter: ArrayAdapter<String>
    private var list = mutableListOf<String>()
    private var id = ""

    private val api: ApiHelper by inject()
    private val retroViewModel : RetroHomeViewModel by viewModel()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_retro_home, container, false)
        (activity as RetroActivity).supportActionBar?.title = "Weather"
        retroViewModel.init()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textAdapter = ArrayAdapter(requireActivity(), R.layout.support_simple_spinner_dropdown_item, list)
        binding.locationTxt.threshold = 1
        binding.locationTxt.setAdapter(textAdapter)

        binding.locationTxt.addTextChangedListener(object: TextWatcher{
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                retroViewModel.callProcess(binding.locationTxt.text.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        })

        binding.goButton.setOnClickListener{
            val query = binding.locationTxt.text.toString()

            if (id != ""){
                println(id)
                val action = RetroHomeFragmentDirections.actionRetroHomeFragmentToWeatherFragment(id)
                findNavController().navigate(action)
            }
        }

        retroViewModel.warning.observe(viewLifecycleOwner, {
                if(it){
                    binding.warningTxt.visibility = View.VISIBLE
                    id = ""

                }else{
                    binding.warningTxt.visibility = View.INVISIBLE
                }
        })

        retroViewModel.list.observe(viewLifecycleOwner, {
            textAdapter.clear()
            textAdapter.addAll(it)
            textAdapter.notifyDataSetChanged()

        })

        retroViewModel.id.observe(viewLifecycleOwner,{
            id = it
        })
    }

}