package com.example.facialdetection.camera

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.facialdetection.MainActivity
import com.example.facialdetection.R
import com.example.facialdetection.Utils
import com.example.facialdetection.databinding.FragmentCameraBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinApiExtension


@KoinApiExtension
class CameraFragment : Fragment() {

    private lateinit var binding: FragmentCameraBinding
    private val cameraViewModel: CameraViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        (activity as MainActivity).supportActionBar?.title =
            getString(R.string.face_detection_header)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_camera, container, false)
        binding.cameraView = cameraViewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cameraViewModel.bmp.observe(viewLifecycleOwner, { newImage ->
            bmpListener(newImage)

        })

        binding.faceName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                binding.addFaceBtn.isEnabled = s.toString() != ""

            }
        })

        cameraViewModel.faceTransform.observe(viewLifecycleOwner, { img ->
            binding.dummy.setImageBitmap(img)

        })

        cameraViewModel.faceNo.observe(viewLifecycleOwner, { faceNo ->
            faceNameListener(faceNo)
        })


        binding.selectImageBtn.setOnClickListener {
            selectImage()


        }
        binding.cameraBtn.setOnClickListener {
            dispatchTakePictureIntent()
        }

        binding.addFaceBtn.setOnClickListener {
            addFaceListener()
        }

    }



    private fun addFaceListener() {
        val faceName = binding.faceName.text.toString()
        val bitmap = binding.dummy.drawable.toBitmap()
        cameraViewModel.onAddFace(requireContext(), faceName, bitmap)
        findNavController().navigate(R.id.action_cameraFragment_to_listFaceFragment)
    }

    private fun bmpListener(newImage: Bitmap?) {
        binding.selectedImage.setImageBitmap(newImage)
        binding.faceNumberText.visibility = View.VISIBLE
        binding.selectedImage.visibility = View.VISIBLE
        binding.detailHeader.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE
    }

    private fun faceNameListener(faceNo: Int?) {
        when (faceNo) {
            1 -> {
                binding.faceName.visibility = View.VISIBLE
                binding.faceName.requestFocus()
            }
            else -> binding.faceName.visibility = View.INVISIBLE
        }
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
            Utils.REQUEST_IMAGE_CAPTURE -> if (resultCode == Activity.RESULT_OK) processImageCapture(
                data
            )

            Utils.REQUEST_IMAGE_PICK -> if (resultCode == Activity.RESULT_OK) processImagePick(data)
        }
    }

    private fun processImageCapture(data: Intent?) {
        binding.progressBar.visibility = View.VISIBLE
        binding.selectedImage.visibility = View.GONE

        var bmp = data?.extras?.get("data") as Bitmap
        bmp = Bitmap.createScaledBitmap(bmp, 224, 224, true) // Rescale the image
        cameraViewModel.updateImage(bmp)
        cameraViewModel.processImage()
    }

    private fun processImagePick(data: Intent?) {
        binding.progressBar.visibility = View.VISIBLE
        binding.selectedImage.visibility = View.GONE
        val img = data?.data
        print(img)
        var bmp =
            BitmapFactory.decodeStream(context?.contentResolver?.openInputStream(img!!)) // convert to Bitmap
        bmp = Bitmap.createScaledBitmap(bmp, 224, 224, true) // Rescale the image
        cameraViewModel.updateImage(bmp)
        cameraViewModel.processImage()
    }


}
