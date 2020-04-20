package com.example.terapanimvo.helper

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.terapanimvo.R
import com.example.terapanimvo.model.JurusanModel
import com.example.terapanimvo.model.Perusahaan
import com.example.terapanimvo.model.PerusahaanModel


class JurusanDetailAdapter(
    private var itemsCells: MutableList<Perusahaan>,
    private var clickListener: (Perusahaan) -> Unit
) :
    RecyclerView.Adapter<JurusanDetailAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
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
        holder.textViewId.text = itemData.perusahaan_id.toString()
        holder.textViewNama.text = itemData.perusahaan_nama
        holder.textViewAlamat.text = itemData.perusahaan_alamat

    }

    //insialisasi untuk list yang akan dibuat menggunakan RecylerView
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var textViewId: TextView
        lateinit var textViewNama: TextView
        lateinit var textViewAlamat: TextView

        init {
            textViewId = itemView.findViewById(R.id.textViewPerusahaanId) as TextView
            textViewNama = itemView.findViewById(R.id.textViewPerusahaanNama) as TextView
            textViewAlamat = itemView.findViewById(R.id.textViewPerusahaanAlamat) as TextView
        }

        //fun untuk memanggil data ketika item diklik
        fun bind(part: Perusahaan, clickListener: (Perusahaan) -> Unit) {
            itemView.setOnClickListener { clickListener(part) }
        }
    }

}