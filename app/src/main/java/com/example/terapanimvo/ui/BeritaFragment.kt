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
import com.example.terapanimvo.R
import com.example.terapanimvo.helper.BeritaAdapter
import com.example.terapanimvo.ip
import com.example.terapanimvo.model.BeritaModel
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class BeritaFragment : Fragment() {

    lateinit var progressBar: ProgressBar
    lateinit var refreshButton: ImageButton

    companion object {
        fun newInstance() = BeritaFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_berita, container, false)
        val recyclerView = view.findViewById(R.id.recyclerViewBerita) as RecyclerView

        progressBar = view.findViewById(R.id.progress_circularBerita)
        refreshButton = view.findViewById(R.id.imageButtonBerita)
        refreshButton.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
        recyclerView.layoutManager = LinearLayoutManager(activity)

        getData(recyclerView)
        return view
    }

    private fun getData(view: RecyclerView){
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
                        val adapter = BeritaAdapter(berita) { beritaItem: BeritaModel ->
                            partItemClicked(beritaItem)
                        }
                        progressBar.visibility = View.GONE
                        view.adapter = adapter
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

    private fun partItemClicked(beritaItem: BeritaModel) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(beritaItem.berita_link))
        startActivity(browserIntent)
    }
}
