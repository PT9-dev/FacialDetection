package com.example.facialdetection.camera

import android.graphics.*
import android.util.Log
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.facialdetection.R
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import com.google.mlkit.vision.face.FaceLandmark


class CameraViewModel: ViewModel() {


    private val _image = MutableLiveData<Bitmap>()
    private val _faceDetails = MutableLiveData<String>()
    val faceDetails: LiveData<String>
        get() = _faceDetails
    private val _faceNo = MutableLiveData<Int>()
    val faceNo: LiveData<Int>
        get() = _faceNo




    private val _bmp = MutableLiveData<Bitmap>()
    val bmp: LiveData<Bitmap>
        get() = _bmp

    val mPaint = Paint().apply {
        isAntiAlias = true
        color = Color.RED
        style = Paint.Style.STROKE
        strokeWidth = 10F
    }

    init {
        Log.i("CameraViewModel", "Model started")
        _faceNo.value=0


    }

    private val callB = object: Drawing{
        override fun draw(faces: MutableList<Face>) {

            _bmp.value = _image.value!!.copy(Bitmap.Config.RGB_565, true)
            val canvas = Canvas(_bmp.value!!)
            _faceDetails.value = addDetails(faces)
            for(face in faces) {
                if(face.smilingProbability != null)
                    println(face.smilingProbability!!)
                else
                    println("========")
                print(face)
                canvas.drawRect(face.boundingBox, mPaint)
            }

        }
    }

    //options for face detection building
    private val options = FaceDetectorOptions.Builder()
        .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
        .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
        .build()





    fun updateImage(bitmap: Bitmap) {
        _image.value = bitmap
        var img: InputImage

    }

    fun processImage(callBack: Drawing = callB){

        val img = InputImage.fromBitmap(_image.value!!, 0)
        val detector = FaceDetection.getClient(options)
        val result = detector.process(img)
            .addOnSuccessListener { faces ->
              callBack.draw(faces)
            }
            .addOnFailureListener { e ->
                // Task failed with an exception
                // ...
                Log.e("FaceDetection", "=> $e")
            }

    }


    private fun calc(face: Face): HashMap<String, String>{
        val map = hashMapOf<String, String>()


        if(face.smilingProbability != null)
        map["Smiling"] = if(face.smilingProbability!! > 0.5) "YES" else "NO"
        else
            map["Smiling"] = "Not Available"

        if(face.leftEyeOpenProbability != null)
            map["Left Eye"] = if(face.smilingProbability!! > 0.5) "OPEN" else "CLOSED"
        else
            map["Left Eye"] = "Not Available"

        if(face.rightEyeOpenProbability != null)
            map["Right Eye"] = if(face.smilingProbability!! > 0.5) "OPEN" else "CLOSED"
        else
            map["Right Eye"] = "Not Available"

        return map


    }

    private fun addCalc(faces: MutableList<Face>): MutableList<HashMap<String, String>>{
        val list = mutableListOf<HashMap<String, String>>()

        for(face in faces){
            list.add(calc(face))
        }
        return list
    }

    private fun addDetails(faces: MutableList<Face>): String{
        var list: HashMap<String, String>
        var str = ""
        var no = 0

        for(face in faces){
            no += 1
            list = calc(face)
            str += "Face $no \n\n"

            for(key in list.keys){
                str += ("$key : ${list[key]} \n")
            }
            str += "\n"

        }

        if(no == 0)
            str = "No faces detected in the image!"

        // set the number of faces
        _faceNo.value = no

        return  str
    }




}

interface Drawing {
    fun draw(faces: MutableList<Face>)
}