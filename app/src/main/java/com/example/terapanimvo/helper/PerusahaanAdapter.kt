package com.example.terapanimvo.helper

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.terapanimvo.R
import com.example.terapanimvo.ip
import com.example.terapanimvo.model.Jurusan
import com.example.terapanimvo.model.JurusanModel
import com.example.terapanimvo.model.PerusahaanModel
import com.squareup.picasso.Picasso

class PerusahaanAdapter(
    private var itemsCells: MutableList<PerusahaanModel>,
    private var clickListener: (PerusahaanModel) -> Unit
) :
    RecyclerView.Adapter<PerusahaanAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.list_perusahaan, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return itemsCells.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemData = itemsCells[position]
        holder.bind(itemData, clickListener)

        val image = itemData.perusahaan_logo
        Picasso.get().load("$ip/images/perusahaan/$image").into(holder.PerusahaanLogo)
        holder.PerusahaanNama.text = itemData.perusahaan_nama
        holder.PerusahaanAlamat.text = itemData.perusahaan_alamat
        holder.PerusahaanId.text = itemData.perusahaan_id.toString()

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var PerusahaanLogo: ImageView
        lateinit var PerusahaanNama: TextView
        lateinit var PerusahaanAlamat: TextView
        lateinit var PerusahaanId: TextView

        init {
            PerusahaanLogo = itemView.findViewById(R.id.imageViewPerusahaanLogo)
            PerusahaanNama = itemView.findViewById(R.id.textViewPerusahaanNama)
            PerusahaanAlamat = itemView.findViewById(R.id.textViewPerusahaanAlamat)
            PerusahaanId = itemView.findViewById(R.id.textViewPerusahaanId)
        }

        fun bind(part: PerusahaanModel, clickListener: (PerusahaanModel) -> Unit) {
            itemView.setOnClickListener { clickListener(part) }
        }
    }

    fun updateList(list: MutableList<PerusahaanModel>) {
        itemsCells = list
        notifyDataSetChanged()
    }

}