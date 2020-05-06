package com.example.terapanimvo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.models.SlideModel
import com.example.terapanimvo.helper.PerusahaanJurusanAdapter
import com.example.terapanimvo.helper.PerusahaanUlasanAdapter
import com.example.terapanimvo.model.Jurusan
import com.example.terapanimvo.model.Perusahaan
import com.example.terapanimvo.model.Ulasan
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_perusahaan_detail.*
import org.json.JSONArray
import org.json.JSONObject

class PerusahaanDetailActivity : AppCompatActivity() {

    lateinit var progressBar: ProgressBar
    lateinit var refreshButton: ImageButton
    lateinit var toolbar: Toolbar
    lateinit var recyclerView: RecyclerView
    lateinit var recyclerView2: RecyclerView

    lateinit var adapter: PerusahaanJurusanAdapter
    lateinit var adapter2: PerusahaanUlasanAdapter

    var perusahaan_id: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perusahaan_detail)

        perusahaan_id = intent.getStringExtra("perusahaan_id").toInt()

        progressBar = findViewById(R.id.progress_circularPerusahaanDetail)
        refreshButton = findViewById(R.id.imageButtonPerusahaanDetail)
        refreshButton.visibility = View.GONE
        toolbar = findViewById(R.id.toolbarPerusahaanDetail)
        setSupportActionBar(toolbar)
        toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_arrow_back_black_24dp)
        toolbar.setNavigationOnClickListener {
            startActivity(Intent(this, PerusahaanActivity::class.java))
        }

        recyclerView = findViewById(R.id.rv_JurusanDibutuhkan)
        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerView2 = findViewById(R.id.rv_Ulasan)
        recyclerView2.layoutManager = LinearLayoutManager(this)

        getData()

    }

    private fun getData()
    {
        var jurusanList = ArrayList<Jurusan>()
        var ulasanList = ArrayList<Ulasan>()
        progressBar.visibility = View.VISIBLE

        AndroidNetworking.get("$ip/perusahaan/$perusahaan_id")
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener
            {
                override fun onResponse(response: JSONObject?)
                {
                    Log.e("kotlinResponse", response.toString())

                    tv_perusahaanNama.text = response!!["perusahaan_nama"].toString()
                    tv_perusahaanAlamat.text = response["perusahaan_alamat"].toString()
                    tv_perusahaanEmail.text = response["perusahaan_email"].toString()
                    tv_perusahaanTelepon.text = response["perusahaan_telepon"].toString()
                    var logo = response["perusahaan_logo"].toString()
                    Picasso.get().load("$ip/images/perusahaan/$logo").into(img_perusahaanLogo)

                    var gambar1 = response["perusahaan_gambar1"].toString()
                    var gambar2 = response["perusahaan_gambar2"].toString()
                    var gambar3 = response["perusahaan_gambar3"].toString()

                    val imageList = ArrayList<SlideModel>()
                        imageList.add(SlideModel("$ip/images/perusahaan/$gambar1", true))
                        imageList.add(SlideModel("$ip/images/perusahaan/$gambar2", true))
                        imageList.add(SlideModel("$ip/images/perusahaan/$gambar3", true))

                    val imageSlider = findViewById<ImageSlider>(R.id.image_slider)
                    imageSlider.setImageList(imageList)

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

        AndroidNetworking.get("$ip/perusahaan/$perusahaan_id")
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener
            {
                override fun onResponse(response: JSONObject?)
                {
                    Log.e("kotlinResponse2", response.toString())
                    val jsonArray: JSONArray = response!!.getJSONArray("jurusan")
                    for (j in 0 until jsonArray.length())
                    {
                        val jsonObject: JSONObject = jsonArray.getJSONObject(j)

                        var jurusan_id = jsonObject.optString("jurusan_id").toInt()
                        var jurusan_nama = jsonObject.optString("jurusan_nama").toString()

                        jurusanList.add(
                            Jurusan(
                                jurusan_id, jurusan_nama
                            )
                        )
                    }

                    adapter = PerusahaanJurusanAdapter(jurusanList)
                    {
                        jurusanItem : Jurusan -> partItemClicked(jurusanItem)
                    }

                    progressBar.visibility = View.GONE
                    recyclerView.adapter = adapter
                }

                override fun onError(anError: ANError?)
                {
                    progressBar.visibility = View.GONE
                    Log.i("_err2", anError.toString())
                    refreshButton.visibility = View.VISIBLE
                    refreshButton.setOnClickListener {
                        getData()
                        refreshButton.visibility = View.GONE
                    }
                }

            })

        AndroidNetworking.get("$ip/perusahaan/$perusahaan_id")
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener
            {
                override fun onResponse(response: JSONObject?)
                {
                    Log.e("kotlinResponse3", response.toString())
                    val jsonArray: JSONArray = response!!.getJSONArray("ulasan")
                    for (k in 0 until jsonArray.length())
                    {
                        val jsonObject: JSONObject = jsonArray.getJSONObject(k)

                        var ulasan_id = jsonObject.optString("ulasan_id").toInt()
                        var ulasan_nama_mhs = jsonObject.optString("ulasan_nama_mhs").toString()
                        var ulasan_jurusan_id = jsonObject.optString("ulasan_jurusan_id").toInt()
                        var ulasan_angkatan = jsonObject.optString("ulasan_angkatan").toString()
                        var ulasan_perusahaan_id = jsonObject.optString("ulasan_perusahaan_id").toInt()
                        var ulasan_periode = jsonObject.optString("ulasan_periode").toString()
                        var ulasan_testimoni = jsonObject.optString("ulasan_testimoni").toString()
                        var ulasan_created_at = jsonObject.optString("created_at").toString()

                        ulasanList.add(
                            Ulasan(
                                ulasan_id, ulasan_nama_mhs, ulasan_jurusan_id, ulasan_angkatan, ulasan_perusahaan_id,
                                ulasan_periode, ulasan_testimoni, ulasan_created_at
                            )
                        )
                    }

                    adapter2 = PerusahaanUlasanAdapter(ulasanList)
                    {
                        ulasanItem : Ulasan -> partItemClicked2(ulasanItem)
                    }

                    progressBar.visibility = View.GONE
                    recyclerView2.adapter = adapter2
                }

                override fun onError(anError: ANError?)
                {
                    progressBar.visibility = View.GONE
                    Log.i("_err3", anError.toString())
                    refreshButton.visibility = View.VISIBLE
                    refreshButton.setOnClickListener {
                        getData()
                        refreshButton.visibility = View.GONE
                    }
                }
            })
    }

    private fun partItemClicked(jurusanItem: Jurusan) {

    }

    private fun partItemClicked2(ulasanItem: Ulasan)
    {
        val intent = Intent(this, PerusahaanDetailUlasanActivity::class.java)
        intent.putExtra("ulasan_id", ulasanItem.ulasan_id.toString())
        startActivity(intent)
    }

}
