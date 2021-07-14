package com.example.facialdetection.camera

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.*
import com.example.facialdetection.Utils
import com.example.facialdetection.database.FaceDao
import com.example.facialdetection.database.FaceTable
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.Face
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


@KoinApiExtension
class CameraViewModel : ViewModel(), KoinComponent {

    // get modules
    private val database: FaceDao by inject()
    private val utils: Utils by inject()

    // declare variables and live data
    private val _faceNo = MutableLiveData<Int>()
    val faceNo: LiveData<Int>
        get() = _faceNo
    private val _bmp = MutableLiveData<Bitmap>()
    val bmp:LiveData<Bitmap>
        get() = _bmp

    // bitmap to find faces
    private val _image = MutableLiveData<Bitmap>()
    // faces in bitmap _image
    private val _faces = MutableLiveData<MutableList<Face>>()


    val faceTransform = Transformations.map(_faces) { faces ->
        _faceNo.postValue(faces.size)

        var bmp = _image.value!!
        if(faces.isNotEmpty())
            bmp = utils.croppedFace(face=faces[0], inputBmp = bmp)

        bmp
    }


    private val mPaint = Paint().apply {
        isAntiAlias = true
        color = Color.GREEN
        style = Paint.Style.STROKE
        strokeWidth = 10F
    }

    // Init block
    init {
        Log.i("CameraViewModel", "Model started")
        _faceNo.value = 0

    }


    fun updateImage(bitmap: Bitmap) {
        _image.value = bitmap
    }


    fun processImage(bitmap: Bitmap = _image.value!!) {

        viewModelScope.launch {
            val bmp = InputImage.fromBitmap(bitmap, 0)
            val faces = utils.detect(bmp)
           callDraw(faces)
            _faces.postValue(faces)
        }
    }


    private fun callDraw(faces: MutableList<Face>) {

        _bmp.value = _image.value!!.copy(Bitmap.Config.RGB_565, true)
        val canvas = Canvas(_bmp.value!!)
        for(face in faces) {
            canvas.drawRect(face.boundingBox, mPaint)
        }

    }

    fun onAddFace(context: Context, name:String, bitmap: Bitmap) {
        viewModelScope.launch {
            val encoding = utils.getEncoding(context, bitmap)
            val encodingString = encoding.joinToString(",")
            val imgUri = withContext(Dispatchers.Default) {
                saveMediaToStorage(
                    context,
                    bitmap,
                    name

                )
            }
            val face = FaceTable(faceName = name, encoding = encodingString, faceUri = imgUri.toString())

            insert(face)
        }
    }


    private suspend fun insert(newFace: FaceTable) {
        withContext(Dispatchers.IO) {
            database.insert(newFace)
        }

    }


    fun saveMediaToStorage(context:Context, bitmap: Bitmap, name: String): Uri? {
        //Generating a file name
        val filename = "$name+${System.currentTimeMillis()}.jpg"

        //Output stream
        var fos: OutputStream? = null
        var imageUri: Uri? = null

        //For devices running android >= Q
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            //getting the contentResolver
            context.contentResolver?.also { resolver ->

                //Content resolver will process the contentvalues
                val contentValues = ContentValues().apply {

                    //putting file information in content values
                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES+"/faceAuthImage")
                }

                //Inserting the contentValues to contentResolver and getting the Uri
                imageUri =
                    resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

                //Opening an outputstream with the Uri that we got
                fos = imageUri?.let { resolver.openOutputStream(it) }
            }
        } else {
            //These for devices running on android < Q
            val imagesDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val image = File(imagesDir, filename)
            fos = FileOutputStream(image)
        }

        fos?.use {
            //Finally writing the bitmap to the output stream that we opened
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
        }

        return imageUri
    }


}

