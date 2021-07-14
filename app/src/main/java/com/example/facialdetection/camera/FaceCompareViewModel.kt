package com.example.facialdetection.camera

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.text.Html
import android.text.Spanned
import androidx.core.text.HtmlCompat
import androidx.lifecycle.*
import com.example.facialdetection.Utils
import com.example.facialdetection.database.FaceDao
import com.google.mlkit.vision.common.InputImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@KoinApiExtension
class FaceCompareViewModel(application: Application) : AndroidViewModel(application),
    KoinComponent {


    // get modules
    private val database: FaceDao by inject()

    // get utils object
    private val utils : Utils by inject()

    // All Live Data
    private val _warningText = MutableLiveData<String>()
    val warningText: LiveData<String>
        get() = _warningText

    private val _bmp = MutableLiveData<Bitmap>()
    val bmp: LiveData<Bitmap>
        get() = _bmp

    private val _bestMatches = MutableLiveData<MutableMap<String, Float>>()
    val bestMatches = Transformations.map(_bestMatches) {
        formatBestMatches(it)
    }

    private fun getContext(): Context {
        return getApplication<Application>().applicationContext
    }

    fun getBestMatches(bitmap: Bitmap) {

        viewModelScope.launch {
            val result = mutableMapOf<String, Float>()
            val allFaces =
                withContext(Dispatchers.Default) { database.getAll() }

            val encoding = utils.getEncoding(getContext(), bitmap)
            val allEncodings = utils.formatEncoding(allFaces)
            var dist: Float
            for (i in 0 until allEncodings.size) {
                dist = utils.distance(encoding, allEncodings[i])

                if (dist <= Utils.THRESHOLD_DISTANCE)
                    result[allFaces[i].faceName] = dist
            }
            _bestMatches.postValue(result)
        }
    }



    private fun getFaceBitmap(bitmap: Bitmap) {
        viewModelScope.launch {
            val img = InputImage.fromBitmap(bitmap, 0)
            val face = utils.detect(img)
            when {
                face.isEmpty() -> _warningText.postValue("No FACE in the image")
                face.size == 1 -> {
                    val faceCrop = utils.croppedFace(bitmap, face = face[0])
                    _bmp.postValue(faceCrop)
                    _warningText.postValue("One face detected and cropped")
                }
                else -> _warningText.postValue("Multiple Faces in the image. Only one face needed!")

            }


        }
    }


    fun processBitmapSelected(bitmap: Bitmap) {
        val bmp = Bitmap.createScaledBitmap(bitmap, 224, 224, true)
        getFaceBitmap(bmp)
    }

    private fun formatBestMatches(map: MutableMap<String, Float>): Spanned {
        val sb = StringBuilder()
        if (map.isNotEmpty()) {
            val sortedMap = map.toList().sortedBy { (_, v) -> v }.toMap()
            sb.apply {
                sortedMap.forEach {
                    append("<br>")
                    append("${it.key} \t\t -> ")
                    append("\t\t ${it.value}<br>")
                }

            }
        } else {
            sb.apply {
                append("No Face Found")
            }
        }

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            Html.fromHtml(sb.toString(), Html.FROM_HTML_MODE_LEGACY)
        else
            HtmlCompat.fromHtml(sb.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
    }

}