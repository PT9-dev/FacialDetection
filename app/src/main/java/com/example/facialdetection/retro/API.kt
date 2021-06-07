package com.example.facialdetection.retro

import com.example.facialdetection.Image
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*


interface API {
    // GET request to get all images
    @GET("fileUpload/")
    fun images(): Call<List<Image?>?>?

    @GET("fileUpload/files/{filename}") // GET request to get an image by its name
    @Streaming
    fun getImageByName(@Path("filename") name: String?): Call<ResponseBody?>?

    @Multipart // POST request to upload an image from storage
    @POST("fileUpload/")
    fun uploadImage(@Part image: MultipartBody.Part?): Call<ResponseBody?>?
}