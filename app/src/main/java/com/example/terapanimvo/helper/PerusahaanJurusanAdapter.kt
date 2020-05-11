package com.example.terapanimvo.helper

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.terapanimvo.R
import com.example.terapanimvo.model.Jurusan

class PerusahaanJurusanAdapter(
    private var itemsCells: MutableList<Jurusan>,
    private var clickListener: (Jurusan) -> Unit
) : RecyclerView.Adapter<PerusahaanJurusanAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_perusahaan_jurusan, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return itemsCells.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemData = itemsCells[position]
        holder.bind(itemData, clickListener)
        holder.textViewIdJurusan.text = itemData.jurusan_id.toString()
        holder.textViewNamaPerusahaan.text = itemData.jurusan_nama
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textViewIdJurusan: TextView
        var textViewNamaPerusahaan: TextView

        init {
            textViewIdJurusan = itemView.findViewById(R.id.tv_JurusanId) as TextView
            textViewNamaPerusahaan = itemView.findViewById(R.id.tv_JurusanNama) as TextView
        }

        fun bind(part: Jurusan, clickListener: (Jurusan) -> Unit) {
            itemView.setOnClickListener { clickListener(part) }
        }
    }

}