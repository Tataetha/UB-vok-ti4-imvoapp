package com.example.terapanimvo.custom

import com.example.terapanimvo.model.JurusanModel
import com.example.terapanimvo.model.PerusahaanModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("jurusan")
    fun getJurusan(): Call<MutableList<JurusanModel>>

    @GET("perusahaan")
    fun getPerusahaan(): Call<MutableList<PerusahaanModel>>
}