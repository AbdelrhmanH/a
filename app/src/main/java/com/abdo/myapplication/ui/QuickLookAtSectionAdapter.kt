package com.abdo.myapplication.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abdo.myapplication.Category
import com.abdo.myapplication.Conclusion
import com.abdo.myapplication.Data
import com.abdo.myapplication.Exclusive
import com.abdo.myapplication.databinding.ItemCategoryBinding

class QuickLookAtSectionAdapter(
    private val context: Context,
    private val data: Data
):RecyclerView.Adapter<QuickLookAtSectionAdapter.VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(
            ItemCategoryBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return data.categories?.size!!.plus(1)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        if (position == 0){
            holder.bind(null,data.exclusive?.toCollection(ArrayList()))
        }else{
            holder.bind(data.categories!![position.minus(1)],null)
        }
    }

    inner class VH(inflate: ItemCategoryBinding) :RecyclerView.ViewHolder(inflate.root){
        private val title = inflate.title
        private val seeMore = inflate.seeMore
        private val rvProducts = inflate.rvProducts

        fun bind(c: Category?, e: ArrayList<Exclusive>?) {
            val titleCategory = if (c == null) "Most Poplar" else c.title
            title.text = titleCategory
            rvProducts.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
                adapter = DynamicAdapter(context,c, e)
                setHasFixedSize(true)
            }
            seeMore.setOnClickListener {
                val intent = Intent(context,CategoryActivity::class.java).apply {
                    putExtra("id", if (e != null) 0 else 1)
                    if (e.isNullOrEmpty()){
                        putExtra("data", c)
                    }else putExtra("data", Conclusion("Most Poplar",e))

                }
                context.startActivity(intent)
            }
        }
    }
}