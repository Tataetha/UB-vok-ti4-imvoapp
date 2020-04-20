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

class BeritaAdapter (
    private var itemsCells: ArrayList<BeritaModel>,
    private val clickListener: (BeritaModel) -> Unit) :
    RecyclerView.Adapter<BeritaAdapter.ViewHolder>() {

    //insialisasi untuk list yang akan dibuat menggunakan RecylerView
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewName = itemView.findViewById(R.id.textViewJudul) as TextView
        val textViewPrice = itemView.findViewById(R.id.textViewUrl) as TextView
        val imageViewBerita = itemView.findViewById(R.id.imageViewBerita) as ImageView

//        val textViewEmail = itemView.findViewById(R.id.textViewEmail) as TextView
//        val textViewAddress = itemView.findViewById(R.id.textViewAddress) as TextView
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

    //fun untuk menghitung keseluruhan data
    override fun getItemCount(): Int {
        return itemsCells.size
    }

    //fun menampilkan data yang diklik
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemsCells[position], clickListener)
        val berita: BeritaModel = itemsCells[position]
//        holder.textViewId.text = berita.beritaId.toString()
        holder.textViewName.text = berita.product_name
        holder.textViewPrice.text = berita.product_price
        val image = berita.product_image
        Picasso.get().load("$ip/images/$image").into(holder.imageViewBerita)
    }
}