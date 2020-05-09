package com.example.terapanimvo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ProgressBar
import androidx.appcompat.widget.Toolbar
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import kotlinx.android.synthetic.main.activity_perusahaan_detail_ulasan.*
import org.json.JSONObject

class PerusahaanDetailUlasanActivity : AppCompatActivity() {
    lateinit var progressBar: ProgressBar
    lateinit var refreshButton: ImageButton
    lateinit var toolbar: Toolbar

    private var ulasan_id: Int = 0
    private var perusahaan_id: Int = 0
    private var jurusan_id: Int = 0
    private var jurusan_nama: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perusahaan_detail_ulasan)

        ulasan_id = intent.getStringExtra("ulasan_id").toInt()

        progressBar = findViewById(R.id.progress_circularPerusahaanDetailUlasan)
        refreshButton = findViewById(R.id.imageButtonPerusahaanDetailUlasan)
        refreshButton.visibility = View.GONE

        toolbar = findViewById(R.id.toolbarPerusahaanDetailUlasan)
        setSupportActionBar(toolbar)
        toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_arrow_back_black_24dp)

        toolbar.setNavigationOnClickListener {
            val intentBack = Intent(this, PerusahaanDetailActivity::class.java)
            intentBack.putExtra("perusahaan_id", perusahaan_id.toString())
            if (intent.getStringExtra("jurusan_id")
                    .toInt() != 0 && intent.getStringExtra("jurusan_nama") != ""
            ) {
                intentBack.putExtra("jurusan_id", jurusan_id.toString())
                intentBack.putExtra("jurusan_nama", jurusan_nama)
            } else {
                intentBack.putExtra("jurusan_id", 0.toString())
                intentBack.putExtra("jurusan_nama", "")
            }
            startActivity(intentBack)
        }

        getData()
    }

    private fun getData() {
        progressBar.visibility = View.VISIBLE

        AndroidNetworking.get("$ip/ulasan/$ulasan_id")
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject?) {
                    Log.e("kotlinResponse", response.toString())

                    tv_D_ulasanTestimoni.text = response!!["ulasan_testimoni"].toString()
                    tv_D_ulasanPeriode.text = response["ulasan_periode"].toString()
                    tv_D_ulasanNama.text = response["ulasan_nama_mhs"].toString()
                    tv_D_ulasanAngkatan.text = response["ulasan_angkatan"].toString()
                    tv_D_ulasanCreated.text = response["created_at"].toString()

                    val perusahaan: JSONObject = response.getJSONObject("perusahaan")
                    perusahaan_id = perusahaan.optString("perusahaan_id").toInt()
                    tv_D_ulasanPerusahaan.text = perusahaan.optString("perusahaan_nama").toString()
                    val jurusan: JSONObject = response.getJSONObject("jurusan")
                    jurusan_id = jurusan.optString("jurusan_id").toInt()
                    jurusan_nama = jurusan.optString("jurusan_nama").toString()
                    tv_D_ulasanJurusan.text = jurusan_nama

                    progressBar.visibility = View.GONE
                }

                override fun onError(anError: ANError?) {
                    progressBar.visibility = View.GONE
                    Log.i("_err", anError.toString())
                    refreshButton.visibility = View.VISIBLE
                    refreshButton.setOnClickListener {
                        getData()
                        refreshButton.visibility = View.GONE
                    }
                }
            })
    }
}
