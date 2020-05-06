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
    val jurusan: ArrayList<Jurusan>,
    val ulasan: ArrayList<Ulasan>
)

class Jurusan(
    val jurusan_id: Int,
    val jurusan_nama: String
)

class Ulasan(
    val ulasan_id: Int,
    val ulasan_nama_mhs: String,
    val ulasan_jurusan_id: Int,
    val ulasan_angkatan: String,
    val ulasan_perusahaan_id: Int,
    val ulasan_periode: String,
    val ulasan_testimoni: String,
    val ulasan_created_at: String
)