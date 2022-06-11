package com.example.captureat.retrofit

import com.example.captureat.FoodModel
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiEndpoint {

    @GET
    fun recipes(): Call<FoodModel>

    @Multipart
    @POST("predict")
    suspend fun uploadImage(
        @Part image: MultipartBody.Part
    ): Response<FoodModel>

}