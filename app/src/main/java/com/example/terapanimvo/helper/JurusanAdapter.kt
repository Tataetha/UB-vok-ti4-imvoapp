package com.example.terapanimvo.helper

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.terapanimvo.R
import com.example.terapanimvo.model.JurusanModel
import com.example.terapanimvo.model.Perusahaan
import com.example.terapanimvo.model.PerusahaanModel
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.collections.ArrayList

class JurusanAdapter(
    private var itemsCells: MutableList<JurusanModel>,
    private var clickListener: (JurusanModel) -> Unit
) :
    RecyclerView.Adapter<JurusanAdapter.ViewHolder>() {

    //fun menampilkan data ke tampilan
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.list_jurusan, parent, false)
        return ViewHolder(itemView)
    }

    //fun untuk menghitung keseluruhan data
    override fun getItemCount(): Int {
        return itemsCells.size
    }

    //fun menampilkan data yang diklik
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemData = itemsCells[position]
        holder.bind(itemData, clickListener)
        holder.textViewId.text = itemData.jurusan_id.toString()
        holder.textViewName.text = itemData.jurusan_nama
    }

    //insialisasi untuk list yang akan dibuat menggunakan RecylerView
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textViewId: TextView
        var textViewName: TextView

        init {
            textViewId = itemView.findViewById(R.id.textViewJurusanId) as TextView
            textViewName = itemView.findViewById(R.id.textViewJurusanNama) as TextView
        }

        fun bind(part: JurusanModel, clickListener: (JurusanModel) -> Unit) {
            itemView.setOnClickListener { clickListener(part) }
        }
    }

    fun updateList(list: MutableList<JurusanModel>) {
        itemsCells = list
        notifyDataSetChanged()
    }
}