package com.abdo.myapplication.ui

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.abdo.myapplication.Category
import com.abdo.myapplication.Exclusive
import com.abdo.myapplication.GlideApp
import com.abdo.myapplication.databinding.ItemProductBinding
import java.io.Serializable

class DynamicAdapter(
    private val context: Context,
    private val category: Category?,
    private var exclusive: ArrayList<Exclusive>?):RecyclerView.Adapter<DynamicAdapter.VH>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemProductBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        if (exclusive != null){
            holder.bind(exclusive!![position])
        }else holder.bind(category!!)
    }

    override fun getItemCount(): Int {
        return exclusive?.size?:category?.sub_categories!!.size
    }

    inner class VH(inflate: ItemProductBinding) :RecyclerView.ViewHolder(inflate.root){
        private val root = inflate.root
        private val image = inflate.image
        private val title = inflate.title
        private val price = inflate.price

        fun bind(e: Exclusive) {
            GlideApp.with(context).load(e.image).into(image)
            title.text = e.title
            price.text = e.price
            root.setOnClickListener {
                Toast.makeText(context,"Clicked", Toast.LENGTH_LONG).show()
            }
        }

        fun bind(c: Category) {
            GlideApp.with(context).load(c.image).into(image)
            title.text = c.title
            price.text = c.id.toString()
            root.setOnClickListener {
                Toast.makeText(context,"Clicked", Toast.LENGTH_LONG).show()
            }
        }
    }
}