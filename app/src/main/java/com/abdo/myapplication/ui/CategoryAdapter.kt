package com.abdo.myapplication.ui

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abdo.myapplication.*
import com.abdo.myapplication.databinding.ItemCategoryBinding
import com.abdo.myapplication.databinding.ItemProductBigBinding

class  CategoryAdapter<T>(
    private val context: Context,
    private val data: List<T>
):RecyclerView.Adapter<CategoryAdapter<T>.VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(
            ItemProductBigBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(data[position])
    }

    inner class VH(inflate: ItemProductBigBinding) :RecyclerView.ViewHolder(inflate.root){
        private val root = inflate.root
        private val image = inflate.image
        private val title = inflate.title
        private val price = inflate.price

        fun bind(d: T) {
            if (d is Exclusive) {
                val e = d as Exclusive
                GlideApp.with(context).load(e.image).into(image)
                title.text = e.title
                price.text = e.price
                root.setOnClickListener {
                    Toast.makeText(context,"Clicked",Toast.LENGTH_LONG).show()
                }
            }else {
                val c = d as SubCategory
                GlideApp.with(context).load(c.image).into(image)
                title.text = c.title
//                price.text = e.price
                root.setOnClickListener {
                    Toast.makeText(context,"Clicked",Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}