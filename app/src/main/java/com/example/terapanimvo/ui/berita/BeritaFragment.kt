package com.example.terapanimvo.ui.berita

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.example.terapanimvo.R
import com.example.terapanimvo.helper.BeritaAdapter
import com.example.terapanimvo.ip
import com.example.terapanimvo.model.BeritaModel
import org.json.JSONArray
import org.json.JSONObject

class BeritaFragment : Fragment() {

    companion object {
        fun newInstance() = BeritaFragment()
    }

//    private lateinit var viewModel: BeritaViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        return inflater.inflate(R.layout.berita_fragment, container, false)
        val root: View = inflater.inflate(R.layout.berita_fragment, container, false)
        val recyclerView = root.findViewById(R.id.recyclerViewBerita) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity)
        val berita = ArrayList<BeritaModel>()
        AndroidNetworking.get("$ip/")
            .setPriority(Priority.MEDIUM).build().getAsJSONObject(object :
                JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    Log.e("kotlinResponse", response.toString())
                    val jsonArray: JSONArray = response.getJSONArray("result")
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject: JSONObject = jsonArray.getJSONObject(i)
//                        Log.e("DAPAT", jsonObject.optString("id_beritahow"))
                        var isi1 = jsonObject.optString("product_name").toString()
                        var isi2 = jsonObject.optString("product_price").toString()
                        var isi3 = jsonObject.optString("product_image").toString()

                        berita.add(BeritaModel("$isi1", "$isi2", "$isi3"))
                    }
                    val adapter = BeritaAdapter(berita) { beritaItem: BeritaModel ->
                        partItemClicked(beritaItem)
                    }
                    recyclerView.adapter = adapter
                }

                override fun onError(anError: ANError?) {
                    Log.i("_err", anError.toString())
                }
            })
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProviders.of(this).get(BeritaViewModel::class.java)
//         TODO: Use the ViewModel
        Log.e("DAPAT", "Hai, ini BeritaFrag ActCreated")
    }

    private fun partItemClicked(beritaItem: BeritaModel) {
        Log.e("DAPAT", "Hai, ini partItemClicked")
    }
}
