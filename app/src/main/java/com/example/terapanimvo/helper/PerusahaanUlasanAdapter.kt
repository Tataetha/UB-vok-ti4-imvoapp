package com.example.terapanimvo.helper

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.terapanimvo.R
import com.example.terapanimvo.model.Ulasan

class PerusahaanUlasanAdapter(
    private var itemsCells: MutableList<Ulasan>,
    private var clickListener: (Ulasan) -> Unit )
    : RecyclerView.Adapter<PerusahaanUlasanAdapter.ViewHolder>()
{
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.list_perusahaan_ulasan, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return itemsCells.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemData = itemsCells[position]
        holder.bind(itemData, clickListener)
        holder.textViewUlasanID.text = itemData.ulasan_id.toString()
        holder.textViewUlasanNama.text = itemData.ulasan_nama_mhs
        holder.textViewUlasanCreated.text = itemData.ulasan_created_at
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        lateinit var textViewUlasanID: TextView
        lateinit var textViewUlasanNama: TextView
        lateinit var textViewUlasanCreated: TextView

        init {
            textViewUlasanID = itemView.findViewById(R.id.tv_ulasanId) as TextView
            textViewUlasanNama = itemView.findViewById(R.id.tv_ulasanNama) as TextView
            textViewUlasanCreated = itemView.findViewById(R.id.tv_ulasanCreated) as TextView
        }

        fun bind(part: Ulasan, clickListener: (Ulasan) -> Unit)
        {
            itemView.setOnClickListener { clickListener(part) }
        }
    }
}