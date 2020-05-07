package com.example.terapanimvo.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import com.example.terapanimvo.PerusahaanDetailActivity
import com.example.terapanimvo.R
import com.example.terapanimvo.helper.ApiClient
import com.example.terapanimvo.helper.BerandaAdapter
import com.example.terapanimvo.helper.BeritaAdapter
import com.example.terapanimvo.ip
import com.example.terapanimvo.model.BeritaModel
import com.example.terapanimvo.model.PerusahaanModel
import com.example.terapanimvo.model.peru
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class BerandaFragment : Fragment() {

    lateinit var progressBar: ProgressBar
    lateinit var refreshButton: ImageButton

    companion object {
        fun newInstance() = BerandaFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater!!.inflate(R.layout.beranda_fragment, container, false)
        val recyclerView = view.findViewById(R.id.recyclerViewPerusahaanHome) as RecyclerView
        val recyclerVieww = view.findViewById(R.id.recyclerViewBeritaHome) as RecyclerView


        progressBar = view.findViewById(R.id.progress_circularPerusahaanHome)
        refreshButton = view.findViewById(R.id.imageButtonPerusahaanHome)
        refreshButton.visibility = View.GONE
        progressBar.visibility = View.VISIBLE

        recyclerVieww.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        recyclerView.layoutManager = LinearLayoutManager(activity)

        getData(recyclerView)
        getDataBerita(recyclerVieww)
        return view
    }
    private fun getData(view: RecyclerView){
        val perusahaan = ArrayList<peru>()
        AndroidNetworking.get("$ip/perusahaan")
            .setPriority(Priority.MEDIUM).build()
            .getAsJSONArray(object :
                JSONArrayRequestListener {
                override fun onResponse(response: JSONArray) {
                    Log.e("kotlinResponse", response.toString())
                    try {
                        for (i in 0 until response.length()) {
                            val jsonObject: JSONObject = response.getJSONObject(i)
                            var isi1 = jsonObject.optString("perusahaan_nama").toString()
                            var isi2 = jsonObject.optString("perusahaan_alamat").toString()
                            var isi3 = jsonObject.optString("perusahaan_logo").toString()
                            var isi4 = jsonObject.optString("perusahaan_id").toString()

                            perusahaan.add(
                                peru("$isi1", "$isi2", "$isi3","$isi4"))
                        }
                        val adapter = BerandaAdapter(perusahaan) { peruItem: peru ->
                            partItemClicked(peruItem)
                        }
                        progressBar.visibility = View.GONE
                        view.adapter = adapter
                    }catch (e: JSONException){
                        progressBar.visibility = View.GONE
                        e.printStackTrace()
                        refreshButton.visibility = View.VISIBLE
                        refreshButton.setOnClickListener {
                            getData(view)
                            refreshButton.visibility = View.GONE
                        }
                    }
                }

                override fun onError(anError: ANError?) {
                    progressBar.visibility = View.GONE
                    Log.i("_err", anError.toString())
                    refreshButton.visibility = View.VISIBLE
                    refreshButton.setOnClickListener {
                        getData(view)
                        refreshButton.visibility = View.GONE
                    }
                }
            })
    }

    private fun getDataBerita(view: RecyclerView){
        val berita = ArrayList<BeritaModel>()
        AndroidNetworking.get("$ip/berita")
            .setPriority(Priority.MEDIUM).build()
            .getAsJSONArray(object :
                JSONArrayRequestListener {
                override fun onResponse(response: JSONArray) {
                    Log.e("kotlinResponse", response.toString())
                    try {
                        for (i in 0 until response.length()) {
                            val jsonObject: JSONObject = response.getJSONObject(i)
                            var isi1 = jsonObject.optString("berita_judul").toString()
                            var isi2 = jsonObject.optString("berita_link").toString()
                            var isi3 = jsonObject.optString("berita_gambar").toString()

                            berita.add(BeritaModel("$isi1", "$isi2", "$isi3"))
                        }
                        val adapterB = BeritaAdapter(berita) { beritaItem: BeritaModel ->
                            partItemClicked(beritaItem)
                        }
                        progressBar.visibility = View.GONE
                        view.adapter = adapterB
                    }catch (e:JSONException){
                        progressBar.visibility = View.GONE
                        e.printStackTrace()
                        refreshButton.visibility = View.VISIBLE
                        refreshButton.setOnClickListener {
                            getData(view)
                            refreshButton.visibility = View.GONE
                        }
                    }
                }

                override fun onError(anError: ANError?) {
                    progressBar.visibility = View.GONE
                    Log.i("_err", anError.toString())
                    refreshButton.visibility = View.VISIBLE
                    refreshButton.setOnClickListener {
                        getData(view)
                        refreshButton.visibility = View.GONE
                    }
                }
            })
    }

    private fun partItemClicked(peruItem: peru) {
        val intent = Intent(activity, PerusahaanDetailActivity::class.java)
        intent.putExtra("perusahaan_id", peruItem.perusahaan_id)
        startActivity(intent)
    }

    private fun partItemClicked(beritaItem: BeritaModel) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(beritaItem.berita_link))
        startActivity(browserIntent)
    }

}
