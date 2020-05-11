package com.example.terapanimvo

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.ImageButton
import android.widget.ProgressBar
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.terapanimvo.helper.ApiClient
import com.example.terapanimvo.helper.PerusahaanAdapter
import com.example.terapanimvo.model.PerusahaanModel
import retrofit2.Call
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class PerusahaanActivity : AppCompatActivity() {
    lateinit var progressBar: ProgressBar
    lateinit var refreshButton: ImageButton
    lateinit var toolbar: Toolbar
    lateinit var recyclerView: RecyclerView

    lateinit var adapter: PerusahaanAdapter
    lateinit var itemList: MutableList<PerusahaanModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perusahaan)

        progressBar = findViewById(R.id.progressPerusahaan)
        refreshButton = findViewById(R.id.imageButtonPerusahaan)
        refreshButton.visibility = View.GONE

        toolbar = findViewById(R.id.toolbarPerusahaan)
        setSupportActionBar(toolbar)
        toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_arrow_back_black_24dp)
        toolbar.setNavigationOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        recyclerView = findViewById(R.id.recyclerViewPerusahaan)
        recyclerView.layoutManager = LinearLayoutManager(this)

        getData()
    }

//    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
//        val search : MenuItem
//        search = menu!!.findItem(R.id.action_searchPerusahaan)
//        if (!getData().isFinalised)
//        {
//            search.setVisible(false)
//        }
//        else
//        {
//            search.setVisible(true)
//        }
//        return true
//    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.perusahaan_menu, menu)

        val manager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchItem = menu?.findItem(R.id.action_searchPerusahaan)
        val searchView = searchItem?.actionView as SearchView

        searchView.setSearchableInfo(manager.getSearchableInfo(componentName))
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.e("INPUT", query.toString())
                filterList(query.toString())
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                filterList(query.toString())
                return true
            }
        })
        return true
    }

    private fun filterList(filterItem: String) {
        var tempList: MutableList<PerusahaanModel> = ArrayList()
        for (d in itemList) {
            val e = Log.e("INPUT", d.toString())
            if (filterItem.toLowerCase(Locale.ROOT) in d.perusahaan_nama.toLowerCase(Locale.ROOT) ||
                filterItem.toLowerCase(Locale.ROOT) in d.perusahaan_alamat.toLowerCase(Locale.ROOT)
            ) {
                tempList.add(d)
            }
        }
        adapter.updateList(tempList)
    }

    private fun getData() {
        progressBar.visibility = View.VISIBLE
        var apiCall = ApiClient.create().getPerusahaan()
        apiCall.enqueue(object : retrofit2.Callback<MutableList<PerusahaanModel>> {
            override fun onFailure(call: Call<MutableList<PerusahaanModel>>, t: Throwable) {
                Log.e("ERROR", "${t.message}")
                progressBar.visibility = View.GONE
                refreshButton.visibility = View.VISIBLE
                refreshButton.setOnClickListener {
                    getData()
                    refreshButton.visibility = View.GONE
                }
            }

            override fun onResponse(
                call: Call<MutableList<PerusahaanModel>>,
                response: Response<MutableList<PerusahaanModel>>
            ) {
                if (response.isSuccessful) {
                    progressBar.visibility = View.GONE
                    itemList = response.body()!!

                    adapter = PerusahaanAdapter(itemList)
                    { perusahaanItem: PerusahaanModel ->
                        partItemClicked(perusahaanItem)
                    }
                    val layoutManager = LinearLayoutManager(this@PerusahaanActivity)
                    recyclerView.layoutManager = layoutManager
                    recyclerView.adapter = adapter
                }
            }

        })
    }

    private fun partItemClicked(perusahaanItem: PerusahaanModel) {
        val intent = Intent(this, PerusahaanDetailActivity::class.java)
        intent.putExtra("perusahaan_id", perusahaanItem.perusahaan_id.toString())
        intent.putExtra("jurusan_id", 0.toString())
        intent.putExtra("jurusan_nama", "")
        startActivity(intent)
    }
}
