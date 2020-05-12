package com.example.terapanimvo.helper

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.terapanimvo.R
import com.example.terapanimvo.ip
import com.example.terapanimvo.model.BeritaModel
import com.squareup.picasso.Picasso

class BerandaBeritaAdapter(
    private var itemsCells: ArrayList<BeritaModel>,
    private val clickListener: (BeritaModel) -> Unit
) :
    RecyclerView.Adapter<BerandaBeritaAdapter.ViewHolder>() {

    //insialisasi untuk list yang akan dibuat menggunakan RecylerView
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewJudul = itemView.findViewById(R.id.textViewJudul) as TextView
        val textViewLink = itemView.findViewById(R.id.textViewUrl) as TextView
        val imageViewBerita = itemView.findViewById(R.id.imageViewBerita) as ImageView

        //fun untuk memanggil data ketika item diklik
        fun bind(part: BeritaModel, clickListener: (BeritaModel) -> Unit) {
            itemView.setOnClickListener { clickListener(part) }
        }
    }

    //fun menampilkan data ke tampilan
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.list_berita, parent, false)
        return ViewHolder(itemView)
    }

    private val limit = 5

    //fun untuk menghitung keseluruhan data
    override fun getItemCount(): Int {
        if (itemsCells.size > limit) {
            return limit;
        } else {
            return itemsCells.size;
        }
    }

    //fun menampilkan data yang diklik
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemsCells[position], clickListener)
        val berita: BeritaModel = itemsCells[position]
        holder.textViewJudul.text = berita.berita_judul
        holder.textViewLink.text = berita.berita_link
        val image = berita.berita_gambar
        Picasso.get().load("$ip/images/berita/$image").into(holder.imageViewBerita)
    }
}