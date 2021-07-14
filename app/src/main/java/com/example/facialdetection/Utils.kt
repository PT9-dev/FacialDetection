package com.example.facialdetection

import android.content.Context
import android.graphics.Bitmap
import com.example.facialdetection.database.FaceTable
import com.example.facialdetection.ml.MobileFaceNet
import com.google.android.gms.tasks.Tasks
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceDetector
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer
import java.nio.ByteOrder
import kotlin.math.sqrt

@KoinApiExtension
class Utils: KoinComponent {

    private val detector: FaceDetector by inject()

    companion object {
        const val REQUEST_IMAGE_CAPTURE = 999
        const val REQUEST_IMAGE_PICK = 99
        const val THRESHOLD_DISTANCE = 1.0f
    }


    private fun preprocessImg(bmp: Bitmap, inputSize: Int = 112): ByteBuffer {
        val imgMean = 128.0f
        val imgStd = 128.0f
        val arr = IntArray(inputSize * inputSize * 3)

        // rescale bitmap
        val bitmap = Bitmap.createScaledBitmap(bmp, inputSize, inputSize, true)

        bitmap.getPixels(arr, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
        val imgData = ByteBuffer.allocateDirect(1 * inputSize * inputSize * 3 * 4)

        imgData.order(ByteOrder.nativeOrder())


        imgData.rewind()

        for (i in 0 until inputSize) {
            for (j in 0 until inputSize) {
                val pixelValue: Int = arr[i * inputSize + j]
                // Quantized model
                imgData.putFloat(((pixelValue shr 16 and 0xFF) - imgMean) / imgStd)
                imgData.putFloat(((pixelValue shr 8 and 0xFF) - imgMean) / imgStd)
                imgData.putFloat(((pixelValue and 0xFF) - imgMean) / imgStd)
            }
        }


        return imgData

    }

    suspend fun detect(img: InputImage): MutableList<Face> {
        return withContext(Dispatchers.IO) {
            val res = Tasks.await(detector.process(img))
            res
        }
    }

    fun formatEncoding(faces: List<FaceTable>?): MutableList<FloatArray> {
        val encodingArray = mutableListOf<FloatArray>()

        if (faces != null) {
            for(face in faces){
                val encodingTransform = face.encoding.split(",")
                    .map { it.toFloat() }
                    .toFloatArray()

                encodingArray.add(encodingTransform)
            }
        }

        return encodingArray

    }

    fun getEncoding(context: Context, bitmap: Bitmap): FloatArray {
        // Preprocess image and get the bytebuffer
        val imgData = preprocessImg(bitmap)
        // Initialize the model
        val model = MobileFaceNet.newInstance(context)
        // Load the input bytebuffer
        val inputFeatures =
            TensorBuffer.createFixedSize(intArrayOf(1, 112, 112, 3), DataType.FLOAT32)
        inputFeatures.loadBuffer(imgData)

        // Runs model inference and gets result.
        val result = model.process(inputFeatures)
        val encoding = result.outputFeature0AsTensorBuffer.floatArray

        model.close()

        // get encodings of the result
        return encoding

    }


    fun croppedFace(inputBmp: Bitmap, face: Face): Bitmap {
        var bmp = inputBmp.copy(Bitmap.Config.RGB_565, true)
        println(bmp)
        val left = if (face.boundingBox.left > 0) face.boundingBox.left else 0
        val top = if (face.boundingBox.top > 0) face.boundingBox.top else 0
        val width =
            if (face.boundingBox.width() < 0) (-1 * face.boundingBox.width()) else face.boundingBox.width()
        val height =
            if (face.boundingBox.height() < 0) (-1 * face.boundingBox.height()) else face.boundingBox.height()

        bmp = Bitmap.createBitmap(
            bmp,
            left,
            top,
            width,
            height

        )

        return bmp

    }

    fun distance(
        encoding1: FloatArray,
        encoding2: FloatArray,
        encodingSize: Int = 192
    ): Float {
        var distance = 0f
        var diff: Float

        for (i in 0 until encodingSize) {
            diff = encoding1[i] - encoding2[i]
            distance += diff * diff
        }
        return sqrt(distance)
    }
}