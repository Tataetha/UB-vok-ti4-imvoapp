package com.example.terapanimvo.custom

import com.example.terapanimvo.model.JurusanModel
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("jurusan")
    fun getJurusan(): Call<MutableList<JurusanModel>>
}