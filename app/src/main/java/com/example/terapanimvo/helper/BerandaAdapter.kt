package com.example.terapanimvo.helper

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.terapanimvo.R
import com.example.terapanimvo.ip
import com.example.terapanimvo.model.PerusahaanBeranda
import com.squareup.picasso.Picasso

class BerandaAdapter(
    private var itemsCells: MutableList<PerusahaanBeranda>,
    private val clickListener: (PerusahaanBeranda) -> Unit
) :
    RecyclerView.Adapter<BerandaAdapter.ViewHolder>() {

    //insialisasi untuk list yang akan dibuat menggunakan RecylerView
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewNama = itemView.findViewById(R.id.textViewPerusahaanNama) as TextView
        val textViewAlamat = itemView.findViewById(R.id.textViewPerusahaanAlamat) as TextView
        val imageViewPerusahaan = itemView.findViewById(R.id.imageViewPerusahaanLogo) as ImageView

        //fun untuk memanggil data ketika item diklik
        fun bind(part: PerusahaanBeranda, clickListener: (PerusahaanBeranda) -> Unit) {
            itemView.setOnClickListener { clickListener(part) }
        }
    }

    //fun menampilkan data ke tampilan
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.list_perusahaan, parent, false)
        return ViewHolder(itemView)
    }

    private val limit = 5

    //fun untuk menghitung keseluruhan data
    override fun getItemCount(): Int {
        return if (itemsCells.size > limit) {
            limit
        } else {
            itemsCells.size
        }
    }

    //fun menampilkan data yang diklik
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemsCells[position], clickListener)
        val perusahaan = itemsCells[position]
        holder.textViewNama.text = perusahaan.perusahaan_nama
        holder.textViewAlamat.text = perusahaan.perusahaan_alamat
        val image = perusahaan.perusahaan_logo
        Picasso.get().load("$ip/images/perusahaan/$image").into(holder.imageViewPerusahaan)
    }
}
