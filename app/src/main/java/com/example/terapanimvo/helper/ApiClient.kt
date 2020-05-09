package com.example.terapanimvo.helper

import com.example.terapanimvo.custom.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

var BASE_URL = "https://imvoadmin.000webhostapp.com"

object ApiClient {

    fun create(): ApiService {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
        return retrofit.create(ApiService::class.java)
    }
}