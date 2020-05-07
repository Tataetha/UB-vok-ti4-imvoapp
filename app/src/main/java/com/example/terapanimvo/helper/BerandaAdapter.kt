package com.example.terapanimvo.helper

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.terapanimvo.R
import com.example.terapanimvo.ip
import com.example.terapanimvo.model.PerusahaanModel
import com.example.terapanimvo.model.peru
import com.squareup.picasso.Picasso

class BerandaAdapter(
    private var itemsCells: MutableList<peru>,
    private val clickListener: (peru) -> Unit) :
    RecyclerView.Adapter<BerandaAdapter.ViewHolder>() {

    //insialisasi untuk list yang akan dibuat menggunakan RecylerView
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewnama = itemView.findViewById(R.id.textViewPerusahaanNama) as TextView
        val textViewalamat = itemView.findViewById(R.id.textViewPerusahaanAlamat) as TextView
        val imageViewperusahaan = itemView.findViewById(R.id.imageViewPerusahaanLogo) as ImageView

        //        val textViewEmail = itemView.findViewById(R.id.textViewEmail) as TextView
//        val textViewAddress = itemView.findViewById(R.id.textViewAddress) as TextView
        //fun untuk memanggil data ketika item diklik
        fun bind(part: peru, clickListener: (peru) -> Unit) {
            itemView.setOnClickListener { clickListener(part) }
        }
    }

    //fun menampilkan data ke tampilan
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.list_perusahaan, parent, false)
        return ViewHolder(itemView)
    }

    private val limit = 3
    //fun untuk menghitung keseluruhan data
    override fun getItemCount(): Int {
        if(itemsCells.size > limit){
            return limit;
        }
        else
        {
            return itemsCells.size;
        }
    }

    //fun menampilkan data yang diklik
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemsCells[position], clickListener)
        val perusahaan: peru = itemsCells[position]
        holder.textViewnama.text = perusahaan.perusahaan_nama
        holder.textViewalamat.text = perusahaan.perusahaan_alamat
        val image = perusahaan.perusahaan_logo
        Picasso.get().load("$ip/images/perusahaan/$image").into(holder.imageViewperusahaan)
    }
}
