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
import com.androidnetworking.interfaces.JSONArrayRequestListener
import com.androidnetworking.interfaces.JSONObjectRequestListener
import kotlinx.android.synthetic.main.activity_perusahaan_detail_ulasan.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class PerusahaanDetailUlasanActivity : AppCompatActivity()
{
    lateinit var progressBar: ProgressBar
    lateinit var refreshButton: ImageButton
    lateinit var toolbar: Toolbar

    private var ulasan_id: Int = 0

    override fun onCreate(savedInstanceState: Bundle?)
    {
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
            startActivity(Intent(this, PerusahaanDetailActivity::class.java))
        }

        getData()
    }

    private fun getData()
    {
        progressBar.visibility = View.VISIBLE

//        AndroidNetworking.get("$ip/ulasan/$ulasan_id")
//            .setPriority(Priority.MEDIUM)
//            .build()
//            .getAsJSONObject(object : JSONObjectRequestListener
//            {
//                override fun onResponse(response: JSONObject?)
//                {
//                    Log.e("kotlinResponse", response.toString())
//                    val jsonArray: JSONArray = response!!.getJSONArray("")
//                    for (i in 0 until jsonArray.length())
//                    {
//                        val jsonObject: JSONObject = jsonArray.getJSONObject(i)
//                        var isi1 = jsonObject.optString("ulasan_testimoni").toString()
//                            var isi2 = jsonObject.optString("ulasan_periode").toString()
//                            var isi3 = jsonObject.optString("ulasan_nama_mhs").toString()
//                            var isi4 = jsonObject.optString("ulasan_angkatan").toString()
//                            var isi5 = jsonObject.optString("created_at").toString()
//                            var isi6 = jsonObject.optString("jurusan_nama").toString()
//                            var isi7 = jsonObject.optString("perusahaan_nama").toString()
//
//                            tv_D_ulasanTestimoni.text = isi1
//                            tv_D_ulasanPeriode.text = isi2
//                            tv_D_ulasanNama.text = isi3
//                            tv_D_ulasan_angkatan.text = isi4
//                            tv_D_ulasanCreated.text = isi5
//
//                            tv_D_ulasanJurusan.text = isi6
//                            tv_D_ulasanPerusahaan.text = isi7
//                            tv_D_ulasan_perusahaanNama.text = isi7
//                        }
//                        progressBar.visibility = View.GONE
//                    }
//
//                override fun onError(anError: ANError?) {
//                    progressBar.visibility = View.GONE
//                    Log.i("_err2", anError.toString())
//                    refreshButton.visibility = View.VISIBLE
//                    refreshButton.setOnClickListener {
//                        getData()
//                        refreshButton.visibility = View.GONE
//                    }
//                }
//            })

//        AndroidNetworking.get("$ip/ulasan/$ulasan_id")
//            .setPriority(Priority.MEDIUM)
//            .build()
//            .getAsJSONArray(object : JSONArrayRequestListener
//            {
//                override fun onResponse(response: JSONArray?)
//                {
//                    Log.e("kotlinResponse", response.toString())
//                    try {
//                        for (i in 0 until response!!.length()) {
//                            val jsonObject: JSONObject = response.getJSONObject(i)
//                            var isi1 = jsonObject.optString("ulasan_testimoni").toString()
//                            var isi2 = jsonObject.optString("ulasan_periode").toString()
//                            var isi3 = jsonObject.optString("ulasan_nama_mhs").toString()
//                            var isi4 = jsonObject.optString("ulasan_angkatan").toString()
//                            var isi5 = jsonObject.optString("created_at").toString()
//                            var isi6 = jsonObject.optString("jurusan_nama").toString()
//                            var isi7 = jsonObject.optString("perusahaan_nama").toString()
//
//                            tv_D_ulasanTestimoni.text = isi1
//                            tv_D_ulasanPeriode.text = isi2
//                            tv_D_ulasanNama.text = isi3
//                            tv_D_ulasan_angkatan.text = isi4
//                            tv_D_ulasanCreated.text = isi5
//
//                            tv_D_ulasanJurusan.text = isi6
//                            tv_D_ulasanPerusahaan.text = isi7
//                            tv_D_ulasan_perusahaanNama.text = isi7
//                        }
//                        progressBar.visibility = View.GONE
//                    }
//                    catch (e: JSONException){
//                        progressBar.visibility = View.GONE
//                        e.printStackTrace()
//                        refreshButton.visibility = View.VISIBLE
//                        refreshButton.setOnClickListener {
//                            getData()
//                            refreshButton.visibility = View.GONE
//                        }
//                    }
//                }
//
//                override fun onError(anError: ANError?)
//                {
//                    progressBar.visibility = View.GONE
//                    Log.i("_err", anError.toString())
//                    refreshButton.visibility = View.VISIBLE
//                    refreshButton.setOnClickListener {
//                        getData()
//                        refreshButton.visibility = View.GONE
//                    }
//                }
//
//            })

        AndroidNetworking.get("$ip/ulasan/$ulasan_id")
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener
            {
                override fun onResponse(response: JSONObject?)
                {
                    Log.e("kotlinResponse", response.toString())

                    tv_D_ulasanTestimoni.text = response!!["ulasan_testimoni"].toString()
                    tv_D_ulasanPeriode.text = response["ulasan_periode"].toString()
                    tv_D_ulasanNama.text = response["ulasan_nama_mhs"].toString()
                    tv_D_ulasan_angkatan.text = response["ulasan_angkatan"].toString()
                    tv_D_ulasanCreated.text = response["created_at"].toString()
//                    tv_D_ulasanJurusan.text = response!!["jurusan_nama"].toString()
//                    tv_D_ulasanPerusahaan.text = response["perusahaan_nama"].toString()
//                    tv_D_ulasan_perusahaanNama.text = response["perusahaan_nama"].toString()

                    progressBar.visibility = View.GONE
                }

                override fun onError(anError: ANError?)
                {
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
