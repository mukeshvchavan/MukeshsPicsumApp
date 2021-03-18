package com.example.lorempicsum

import retrofit2.Call
import retrofit2.http.GET

interface PicsumInterface {

    @GET("/list")
    fun getImages(): Call<List<ImageData>>
}