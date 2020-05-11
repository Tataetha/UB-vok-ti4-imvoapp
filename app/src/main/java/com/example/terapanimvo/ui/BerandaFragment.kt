package com.example.terapanimvo.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import com.example.terapanimvo.PerusahaanActivity
import com.example.terapanimvo.PerusahaanDetailActivity
import com.example.terapanimvo.R
import com.example.terapanimvo.helper.BerandaAdapter
import com.example.terapanimvo.helper.BeritaAdapter
import com.example.terapanimvo.helper.BeritaAdapter2
import com.example.terapanimvo.ip
import com.example.terapanimvo.model.BeritaModel
import com.example.terapanimvo.model.PerusahaanBeranda
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class BerandaFragment : Fragment() {

    lateinit var progressBar: ProgressBar
    lateinit var refreshButton: ImageButton

    val perusahaan = ArrayList<PerusahaanBeranda>()
    val berita = ArrayList<BeritaModel>()

    companion object {
        fun newInstance() = BerandaFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater!!.inflate(R.layout.fragment_beranda, container, false)
        val recyclerViewJurusan = view.findViewById(R.id.recyclerViewPerusahaanHome) as RecyclerView
        val recyclerViewBerita = view.findViewById(R.id.recyclerViewBeritaHome) as RecyclerView
        val btnPerusahaanMore = view.findViewById(R.id.btn_perusahaanMore) as Button
        val btnBeritaMore = view.findViewById(R.id.btn_beritaMore) as Button

        progressBar = view.findViewById(R.id.progress_circularPerusahaanHome)
        refreshButton = view.findViewById(R.id.imageButtonPerusahaanHome)
        refreshButton.visibility = View.GONE
        progressBar.visibility = View.VISIBLE

        recyclerViewBerita.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewJurusan.layoutManager = LinearLayoutManager(activity)

        getDataJurusan(recyclerViewJurusan)
        getDataBerita(recyclerViewBerita)

        btnPerusahaanMore.setOnClickListener {
            val intent = Intent(this@BerandaFragment.context, PerusahaanActivity::class.java)
            startActivity(intent)
        }

        btnBeritaMore.setOnClickListener {
            requireFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, BeritaFragment()).commit()
        }

        return view
    }

    private fun getDataJurusan(view: RecyclerView) {
        AndroidNetworking.get("$ip/perusahaan")
            .setPriority(Priority.MEDIUM).build()
            .getAsJSONArray(object :
                JSONArrayRequestListener {
                override fun onResponse(response: JSONArray) {
                    Log.e("kotlinResponse", response.toString())
                    try {
                        for (i in 0 until response.length()) {
                            val jsonObject: JSONObject = response.getJSONObject(i)
                            var perusahaan_id = jsonObject.optString("perusahaan_id").toInt()
                            var perusahaan_nama = jsonObject.optString("perusahaan_nama").toString()
                            var perusahaan_alamat = jsonObject.optString("perusahaan_alamat").toString()
                            var perusahaan_logo = jsonObject.optString("perusahaan_logo").toString()

                            perusahaan.add(
                                PerusahaanBeranda(
                                    perusahaan_id,
                                    "$perusahaan_nama",
                                    "$perusahaan_alamat",
                                    "$perusahaan_logo"
                                )
                            )
                        }
                        val adapter = BerandaAdapter(perusahaan) { peruItem: PerusahaanBeranda ->
                            partItemClicked(peruItem)
                        }
                        progressBar.visibility = View.GONE
                        view.adapter = adapter
                    } catch (e: JSONException) {
                        progressBar.visibility = View.GONE
                        e.printStackTrace()
                        refreshButton.visibility = View.VISIBLE
                        refreshButton.setOnClickListener {
                            getDataJurusan(view)
                            refreshButton.visibility = View.GONE
                        }
                    }
                }

                override fun onError(anError: ANError?) {
                    progressBar.visibility = View.GONE
                    Log.i("_err", anError.toString())
                    refreshButton.visibility = View.VISIBLE
                    refreshButton.setOnClickListener {
                        getDataJurusan(view)
                        refreshButton.visibility = View.GONE
                    }
                }
            })
    }

    private fun getDataBerita(view: RecyclerView) {
        AndroidNetworking.get("$ip/berita")
            .setPriority(Priority.MEDIUM).build()
            .getAsJSONArray(object :
                JSONArrayRequestListener {
                override fun onResponse(response: JSONArray) {
                    Log.e("kotlinResponse", response.toString())
                    try {
                        for (i in 0 until response.length()) {
                            val jsonObject: JSONObject = response.getJSONObject(i)
                            var berita_judul = jsonObject.optString("berita_judul").toString()
                            var berita_link = jsonObject.optString("berita_link").toString()
                            var berita_gambar = jsonObject.optString("berita_gambar").toString()

                            berita.add(
                                BeritaModel(
                                    "$berita_judul",
                                    "$berita_link",
                                    "$berita_gambar"
                                )
                            )
                        }
                        val adapterB = BeritaAdapter2(berita) { beritaItem: BeritaModel ->
                            partItemClicked(beritaItem)
                        }
                        progressBar.visibility = View.GONE
                        view.adapter = adapterB
                    } catch (e: JSONException) {
                        progressBar.visibility = View.GONE
                        e.printStackTrace()
                        refreshButton.visibility = View.VISIBLE
                        refreshButton.setOnClickListener {
                            getDataJurusan(view)
                            refreshButton.visibility = View.GONE
                        }
                    }
                }

                override fun onError(anError: ANError?) {
                    progressBar.visibility = View.GONE
                    Log.i("_err", anError.toString())
                    refreshButton.visibility = View.VISIBLE
                    refreshButton.setOnClickListener {
                        getDataJurusan(view)
                        refreshButton.visibility = View.GONE
                    }
                }
            })
    }

    private fun partItemClicked(perusahaanItem: PerusahaanBeranda) {
        val intent = Intent(activity, PerusahaanDetailActivity::class.java)
        intent.putExtra("perusahaan_id", perusahaanItem.perusahaan_id.toString())
        intent.putExtra("jurusan_id", 0.toString())
        intent.putExtra("jurusan_nama", "")
        startActivity(intent)
    }

    private fun partItemClicked(beritaItem: BeritaModel) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(beritaItem.berita_link))
        startActivity(browserIntent)
    }

}
