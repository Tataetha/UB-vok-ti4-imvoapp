package com.example.terapanimvo.model

class PerusahaanModel(
    val perusahaan_id: Int,
    val perusahaan_nama: String,
    val perusahaan_alamat: String,
    val perusahaan_email: String,
    val perusahaan_telepon: String,
    val perusahaan_logo: String,
    val perusahaan_gambar1: String,
    val perusahaan_gambar2: String,
    val perusahaan_gambar3: String,
    val jurusan: MutableList<JurusanModel>
)