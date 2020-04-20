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
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.collections.ArrayList

class JurusanAdapter(
    private var itemsCells: MutableList<JurusanModel>,
//    private var itemsCellsFiltered: ArrayList<JurusanModel>,
    private var clickListener: (JurusanModel) -> Unit) :
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
        holder.textViewName.text = itemData.jurusan_nama
//        holder.textViewId.text = berita.beritaId.toString()
//        holder.textViewPrice.text = berita.product_price
//        val image = berita.product_image
//        Picasso.get().load("$ip/images/$image").into(holder.imageViewBerita)
    }

    //insialisasi untuk list yang akan dibuat menggunakan RecylerView
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var textViewName: TextView

        init {
            textViewName = itemView.findViewById(R.id.textViewTitleJurusan) as TextView
            //fun untuk memanggil data ketika item diklik
            }
        fun bind(part: JurusanModel, clickListener: (JurusanModel) -> Unit) {
            itemView.setOnClickListener { clickListener(part) }
        }
    }

    fun updateList(list: MutableList<JurusanModel>){
        itemsCells = list
        notifyDataSetChanged()
    }

//    override fun getFilter(): Filter {
//        return object :Filter(){
//            override fun performFiltering(charSequence: CharSequence?): FilterResults {
//                val charString = charSequence.toString()
//                if (charString.isEmpty()) {
//                    itemsCellsFiltered = itemsCells
//                } else {
//                    val filteredList: ArrayList<JurusanModel> =
//                        java.util.ArrayList<JurusanModel>()
//                    for (row in itemsCells) {
//
//                        // name match condition. this might differ depending on your requirement
//                        // here we are looking for name or phone number match
//                        if (row.jurusan_nama.toLowerCase(Locale.ROOT)
//                                .contains(charString.toLowerCase(Locale.ROOT))
////                            || row.getPhone().contains(charSequence)
//                        ) {
//                            filteredList.add(row)
//                        }
//                    }
//                    itemsCellsFiltered = filteredList
//                }
//                val filterResults = FilterResults()
//                filterResults.values = itemsCellsFiltered
//                return filterResults
//            }
//
//            override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults?) {
//                itemsCellsFiltered = filterResults?.values as ArrayList<JurusanModel>
//                notifyDataSetChanged()
//            }
//        }
//    }
}