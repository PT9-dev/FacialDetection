package com.example.facialdetection.camera

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.*
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.facialdetection.MainActivity
import com.example.facialdetection.R
import com.example.facialdetection.databinding.FragmentCameraBinding


class CameraFragment : Fragment() {

    private lateinit var binding: FragmentCameraBinding
    private lateinit var viewModel : CameraViewModel

    companion object {
        const val REQUEST_IMAGE_CAPTURE = 999
        const val REQUEST_IMAGE_PICK = 99
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{

        (activity as MainActivity).supportActionBar?.title = getString(R.string.face_detection_header)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_camera, container, false)
        viewModel = ViewModelProvider(this).get(CameraViewModel::class.java)
        binding.cameraView = viewModel
        binding.lifecycleOwner = this





        // Set action bar title


        viewModel.bmp.observe(viewLifecycleOwner, Observer { newImage ->
            binding.selectedImage.setImageBitmap(newImage)
            binding.faceNumberText.visibility = View.VISIBLE
            binding.selectedImage.visibility = View.VISIBLE
            binding.detailHeader.visibility = View.VISIBLE
            binding.progressBar.visibility = View.GONE

        })

        viewModel.faceDetails.observe(viewLifecycleOwner, Observer { msg ->
            binding.detailText.text = msg

        })


        binding.selectImageBtn.setOnClickListener{
            selectImage()

        }
        binding.cameraBtn.setOnClickListener{
            dispatchTakePictureIntent()
        }
        return binding.root
    }


    private fun dispatchTakePictureIntent(){

        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        }catch (e: ActivityNotFoundException){
            // Display message for activity not found
        }


    }

    private fun selectImage(){
        val pickPhoto = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        try {
            startActivityForResult(pickPhoto, REQUEST_IMAGE_PICK)

        }catch (e: ActivityNotFoundException){
            // Display message for activity not found
        }

    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        when(requestCode){
            REQUEST_IMAGE_CAPTURE -> {
                if (resultCode == Activity.RESULT_OK) {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.selectedImage.visibility = View.GONE

                    var bmp = data?.extras?.get("data") as Bitmap
                    bmp = Bitmap.createScaledBitmap(bmp, 500, 600, true) // Rescale the image
                    viewModel.updateImage(bmp)
                    viewModel.processImage()


                }
            }
            REQUEST_IMAGE_PICK ->{
                if (resultCode == Activity.RESULT_OK) {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.selectedImage.visibility = View.GONE
                    val img = data?.data
                    var bmp = BitmapFactory.decodeStream(context?.contentResolver?.openInputStream(img!!)) // convert to Bitmap
                    bmp = Bitmap.createScaledBitmap(bmp, 500, 600, true) // Rescale the image
                    viewModel.updateImage(bmp)
                    viewModel.processImage()

                }
            }

        }
    }

}
