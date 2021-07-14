package com.example.facialdetection.camera

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.facialdetection.R
import com.example.facialdetection.camera.adapters.ListFaceAdapter
import com.example.facialdetection.databinding.FragmentListFaceBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class ListFaceFragment : Fragment() {
    private lateinit var binding: FragmentListFaceBinding
    private val listFaceViewModel: ListFaceViewModel by viewModel()
    private val adapter = ListFaceAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list_face, container, false)
        binding.lifecycleOwner = this
        binding.listFaceVIew = listFaceViewModel
        binding.recycleList.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listFaceViewModel.allFaces.observe(viewLifecycleOwner, {
            adapter.data = it
        })

        binding.addButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFaceFragment_to_cameraFragment)
        }

        binding.compareButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFaceFragment_to_faceCompareFragment)
        }

        binding.clearBtn.setOnClickListener {
            listFaceViewModel.clearAll()
        }

    }
}