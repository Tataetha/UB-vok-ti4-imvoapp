package com.example.terapanimvo.custom

import com.example.terapanimvo.model.JurusanModel
import com.example.terapanimvo.model.PerusahaanModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    //JURUSAN
    //get jurusan
    @GET("jurusan")
    fun getJurusan(): Call<MutableList<JurusanModel>>

    //PERUSAHAAN
    //get perusahaan
    @GET("perusahaan")
    fun getPerusahaan(): Call<MutableList<PerusahaanModel>>

}