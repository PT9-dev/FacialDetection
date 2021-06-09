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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RetroHomeFragment : Fragment() {
    lateinit var binding: FragmentRetroHomeBinding
    private lateinit var textAdapter: ArrayAdapter<String>
    private var list = mutableListOf("yuiuo", "jbhkihjbn")



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_retro_home, container, false)

        textAdapter = ArrayAdapter(requireActivity(), R.layout.support_simple_spinner_dropdown_item, list)
        binding.locationTxt.threshold = 1
        binding.locationTxt.setAdapter(textAdapter)

        binding.locationTxt.addTextChangedListener(object: TextWatcher{
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                process()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        })

        binding.uploadImageBtn.setOnClickListener{
            val query = binding.locationTxt.text.toString()

            if (query != ""){
           val action = RetroHomeFragmentDirections.actionRetroHomeFragmentToWeatherFragment(query)
            findNavController().navigate(action)
            }
        }


        return binding.root
    }

    fun process(){
        val queryLocation = binding.locationTxt.text.toString()
        if(queryLocation != ""){
            val service = RetrofitClient().getIdAPI()
            val call = service.IdOf(queryLocation)
            call.enqueue(object: Callback<WeatherId?> {
                override fun onResponse(
                    call: Call<WeatherId?>,
                    response: Response<WeatherId?>
                ) {
                    list.clear()
                    textAdapter.clear()
                    if (response.body() != null) {
                        for (item in response.body()!!) {
                            list.add(item.title)
                        }
                        textAdapter.addAll(list)
                        textAdapter.notifyDataSetChanged()
                        val id = response.body()?.get(0)!!.woeid
                        println(id)
                    }else{

                    }
                }

                override fun onFailure(call: Call<WeatherId?>, t: Throwable) {
                   print("Error")
                }
            })



        }else{
            print("======================")
        }

    }



}