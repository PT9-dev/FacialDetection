package com.example.facialdetection.camera

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.databinding.DataBindingUtil
import com.example.facialdetection.R
import com.example.facialdetection.Utils
import com.example.facialdetection.databinding.FragmentFaceCompareBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinApiExtension


@KoinApiExtension
class FaceCompareFragment : Fragment() {
    private lateinit var binding: FragmentFaceCompareBinding
    private val faceCompareViewModel: FaceCompareViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_face_compare, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.selectImageBtnCompare.setOnClickListener {
            selectImage()
        }

        binding.cameraBtnCompare.setOnClickListener {
            dispatchTakePictureIntent()
        }

        binding.confirmCompareBtn.setOnClickListener {
            val bmp = binding.imageToCompare.drawable.toBitmap()
            faceCompareViewModel.getBestMatches(bmp)
        }

        faceCompareViewModel.bmp.observe(viewLifecycleOwner, { bmp ->
            binding.imageToCompare.setImageBitmap(bmp)
        })

        faceCompareViewModel.warningText.observe(viewLifecycleOwner, { msg ->
            binding.warningTextCompare.text = msg
        })

        faceCompareViewModel.bestMatches.observe(viewLifecycleOwner, { msg ->
            binding.similarFacesText.text = msg
        })

    }

    private fun dispatchTakePictureIntent() {

        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, Utils.REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {
            // Display message for activity not found
        }


    }

    private fun selectImage() {
        val pickPhoto = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        try {
            startActivityForResult(pickPhoto, Utils.REQUEST_IMAGE_PICK)

        } catch (e: ActivityNotFoundException) {
            // Display message for activity not found
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        when (requestCode) {
            Utils.REQUEST_IMAGE_CAPTURE -> {
                if (resultCode == Activity.RESULT_OK) {
                    val bmp = data?.extras?.get("data") as Bitmap
                    faceCompareViewModel.processBitmapSelected(bmp)

                }
            }
            Utils.REQUEST_IMAGE_PICK -> {
                if (resultCode == Activity.RESULT_OK) {
                    val img = data?.data
                    val bmp =
                        BitmapFactory.decodeStream(context?.contentResolver?.openInputStream(img!!)) // convert to Bitmap
                        faceCompareViewModel.processBitmapSelected(bmp)

                }
            }

        }
    }


}