package com.example.terapanimvo

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.example.terapanimvo.helper.ApiClient
import com.example.terapanimvo.helper.JurusanAdapter
import com.example.terapanimvo.model.JurusanModel
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class JurusanActivity : AppCompatActivity() {

//    val jurusan = ArrayList<JurusanModel>()
//    val adapter = JurusanAdapter(jurusan, jurusan) { jurusanItem: JurusanModel ->
//        partItemClicked(jurusanItem)
//    }

    lateinit var progressBar: ProgressBar
    lateinit var toolbar: Toolbar
    lateinit var recyclerView: RecyclerView

    lateinit var adapter: JurusanAdapter
    lateinit var itemList: MutableList<JurusanModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jurusan)

        progressBar = findViewById(R.id.progress_circularJurusan)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_arrow_back_black_24dp)
        toolbar.setNavigationOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        recyclerView = findViewById(R.id.recyclerViewJurusan)
        recyclerView.layoutManager = LinearLayoutManager(this)

        getData()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.jurusan_menu, menu)

        val manager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchItem = menu?.findItem(R.id.action_searchJurusan)
        val searchView = searchItem?.actionView as SearchView

        searchView.setSearchableInfo(manager.getSearchableInfo(componentName))
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(query: String?): Boolean {
//                adapter.getFilter().filter(query)
//                Toast.makeText(this@JurusanActivity,"Hai, $query",Toast.LENGTH_SHORT).show()
                Log.e("INPUT", query.toString())
                filterList(query.toString())
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
//                searchView.clearFocus()
//                searchView.setQuery("",false)
//                searchView.onActionViewCollapsed()
//                Toast.makeText(this@JurusanActivity,"Hai, $query",Toast.LENGTH_SHORT).show()
//                adapter.getFilter().filter(query)
                filterList(query.toString())
                return true
            }
        })
        return true
    }

    private fun filterList(filterItem: String) {
        var tempList: MutableList<JurusanModel> = ArrayList()
        for (d in itemList) {
            val e = Log.e("INPUT", d.toString())
            if (filterItem.toLowerCase(Locale.ROOT) in d.jurusan_nama.toLowerCase(Locale.ROOT)) {
                tempList.add(d)
            }
        }
        adapter.updateList(tempList)
    }

    private fun getData() {
        progressBar.visibility = View.VISIBLE
//        AndroidNetworking.get("$ip/jurusan")
//            .setPriority(Priority.MEDIUM).build().getAsJSONObject(object :
//                JSONObjectRequestListener {
//                override fun onResponse(response: JSONObject) {
//                    Log.e("kotlinResponse", response.toString())
//                    val jsonArray: JSONArray = response.getJSONArray("result")
//                    for (i in 0 until jsonArray.length()) {
//                        val jsonObject: JSONObject = jsonArray.getJSONObject(i)
////                        Log.e("DAPAT", jsonObject.optString("id_jurusan"))
//                        var isi1 = jsonObject.optString("jurusan_nama").toString()
//
//                        itemList.add(JurusanModel("$isi1"))
//                    }
//                    adapter = JurusanAdapter(itemList) { jurusanItem: JurusanModel ->
//                        partItemClicked(jurusanItem)
//                    }
//                    progressBar.visibility = View.GONE
//                    recyclerView.adapter = adapter
//                }
//
//                override fun onError(anError: ANError?) {
//                    Log.i("_err", anError.toString())
//                }
//            })
        var apiCall = ApiClient.create().getJurusan()
        apiCall.enqueue(object : retrofit2.Callback<MutableList<JurusanModel>> {
            override fun onFailure(call: Call<MutableList<JurusanModel>>, t: Throwable) {
                Log.e("ERROR", "${t.message}")
                progressBar.visibility = View.GONE
            }

            override fun onResponse(
                call: Call<MutableList<JurusanModel>>,
                response: Response<MutableList<JurusanModel>>
            ) {
                if (response.isSuccessful) {
                    progressBar.visibility = View.GONE
                    itemList = response.body()!!
                    Log.e("OUTPUT", "$itemList")

                    adapter = JurusanAdapter(itemList) { jurusanItem: JurusanModel ->
                        partItemClicked(jurusanItem)
                    }
                    val layoutManager = LinearLayoutManager(this@JurusanActivity)
                    recyclerView.layoutManager = layoutManager
                    recyclerView.adapter = adapter
                }
            }
        })
    }

    private fun partItemClicked(jurusanItem: JurusanModel) {

    }
}
