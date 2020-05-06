package com.example.terapanimvo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.example.terapanimvo.helper.JurusanDetailAdapter
import com.example.terapanimvo.model.JurusanModel
import com.example.terapanimvo.model.Perusahaan
import org.json.JSONArray
import org.json.JSONObject
import kotlin.collections.ArrayList


class JurusanDetailActivity : AppCompatActivity() {

    lateinit var progressBar: ProgressBar
    lateinit var refreshButton: ImageButton
    lateinit var toolbar: Toolbar
    lateinit var recyclerView: RecyclerView
    lateinit var textViewDetailJurusan: TextView

    lateinit var adapter: JurusanDetailAdapter

    var jurusan_id: Int = 0
    var jurusan_nama: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jurusan_detail)

        jurusan_id = intent.getStringExtra("jurusan_id").toInt()
        jurusan_nama = intent.getStringExtra("jurusan_nama")

        textViewDetailJurusan = findViewById(R.id.textViewJurusanDetailNama)
        textViewDetailJurusan.text = jurusan_nama

        progressBar = findViewById(R.id.progress_circularJurusanDetail)
        refreshButton = findViewById(R.id.imageButtonJurusanDetail)
        refreshButton.visibility = View.GONE
        toolbar = findViewById(R.id.toolbarJurusanDetail)
        setSupportActionBar(toolbar)
        toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_arrow_back_black_24dp)
        toolbar.setNavigationOnClickListener {
            startActivity(Intent(this, JurusanActivity::class.java))
        }

        recyclerView = findViewById(R.id.recyclerViewJurusanDetail)
        recyclerView.layoutManager = LinearLayoutManager(this)

        getData()
    }

    private fun getData() {
        var perusahaanList = ArrayList<Perusahaan>()
        progressBar.visibility = View.VISIBLE
        AndroidNetworking.get("$ip/jurusan/$jurusan_id")
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener
            {
                override fun onResponse(response: JSONObject) {
                    Log.e("kotlinResponse", response.toString())
                    val jsonArray: JSONArray = response!!.getJSONArray("perusahaan")
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject: JSONObject = jsonArray.getJSONObject(i)
                        var isi1 = jsonObject.optString("perusahaan_id").toInt()
                        var isi2 = jsonObject.optString("perusahaan_nama").toString()
                        var isi3 = jsonObject.optString("perusahaan_alamat").toString()
                        var isi4 = jsonObject.optString("perusahaan_email").toString()
                        var isi5 = jsonObject.optString("perusahaan_telepon").toString()
                        var isi6 = jsonObject.optString("perusahaan_logo").toString()
                        var isi7 = jsonObject.optString("perusahaan_gambar1").toString()
                        var isi8 = jsonObject.optString("perusahaan_gambar2").toString()
                        var isi9 = jsonObject.optString("perusahaan_gambar3").toString()

                        perusahaanList.add(
                            Perusahaan(
                                isi1, "$isi2", "$isi3",
                                "$isi4", "$isi5", "$isi6",
                                "$isi7", "$isi8", "$isi9"
                            )
                        )
                    }
                    adapter = JurusanDetailAdapter(perusahaanList) { jurusanItem: Perusahaan ->
                        partItemClicked(jurusanItem)
                    }
                    progressBar.visibility = View.GONE
                    recyclerView.adapter = adapter
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

    private fun partItemClicked(perusahaanItem: Perusahaan) {
        val intent = Intent(this, PerusahaanDetailActivity::class.java)
        intent.putExtra("perusahaan_id", perusahaanItem.perusahaan_id.toString())
        startActivity(intent)
    }
}
