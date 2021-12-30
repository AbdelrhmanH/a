package com.abdo.myapplication.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.recyclerview.widget.GridLayoutManager
import com.abdo.myapplication.*
import com.abdo.myapplication.databinding.ActivityCategoryBinding
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.google.android.material.chip.ChipGroup

class CategoryActivity : AppCompatActivity() {

    companion object{
        private val TAG = CategoryActivity::class.java.simpleName
    }

    private lateinit var binding: ActivityCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarCustom)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        if (intent.extras != null){
            if (intent.getIntExtra("id",-1) == 0){
                init(intent.getSerializableExtra("data") as Conclusion)
            }else{
                init(intent.getSerializableExtra("data") as Category)
            }
        }
    }

    private fun init(data: Conclusion) {
        binding.toolbarTitle.text = data.titleCategory
        if (!data.exclusive.isNullOrEmpty()) {
            binding.rcItems.apply {
                layoutManager = GridLayoutManager(this@CategoryActivity, 2).apply {
                    spanSizeLookup = object :GridLayoutManager.SpanSizeLookup(){
                        override fun getSpanSize(position: Int): Int {
                            return if (position == 0) 2 else 1
                        }
                    }
                }
                adapter = CategoryAdapter<Exclusive>(context,data.exclusive)
                setHasFixedSize(true)
            }
        }
    }

    private fun init(data: Category) {
        binding.toolbarTitle.text = data.title
        if (!data.sub_categories.isNullOrEmpty()) {
            binding.rcItems.apply {
                layoutManager = GridLayoutManager(this@CategoryActivity, 2)
                adapter = CategoryAdapter<SubCategory>(context,data.sub_categories)
                setHasFixedSize(true)
            }
        }

        if (data.sub_categories!!.isNotEmpty()){
//            createChip(data.sub_categories)
            createGroup(data.sub_categories)
        }

    }

    private fun createGroup(subCategories: List<SubCategory>){
        Log.d(TAG, "createChip: New Category Group")
        val storeID = ArrayList<Int>()
        val ll = binding.containerChipGroup
        val rg = RadioGroup(this).apply {
            id = View.generateViewId()
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
            setPadding(0,5,0,5)
            orientation = LinearLayout.HORIZONTAL
        }
        val radioButton_All = layoutInflater.inflate(R.layout.item_radio_button,rg,false) as RadioButton
        radioButton_All.apply {
            id = View.generateViewId()
            storeID.add(id)
            text = "All"
            isChecked = true
        }
        rg.addView(radioButton_All)
        subCategories.forEach {
            val radioButton = layoutInflater.inflate(R.layout.item_radio_button,rg,false) as RadioButton
            radioButton.apply {
                id = View.generateViewId()
                storeID.add(id)
                text = it.title
            }
            rg.addView(radioButton)
        }
        rg.setOnCheckedChangeListener { radioGroup, i ->
            val id = radioGroup.checkedRadioButtonId
            val childCount = ll.childCount
            val index = ll.children.indexOf(rg)
            when(id){
                radioButton_All.id ->{
                    if (index < childCount-1) {
                        ll.removeViews(index+1,childCount-(index+1))
                    }
                    // here code set list for adapter
                }
                else ->{
                    val subCategory = subCategories[getIndexById(storeID,id)].sub_categories
                    if (!subCategory.isNullOrEmpty()){
                        if (index < childCount-1) {
                            ll.removeViews(index+1,childCount-(index+1))
                        }
                        createGroup(subCategory)
                    }
                    // here code set list for adapter
                }
            }
        }
        ll.addView(rg)
    }

    private fun getIndexById(storeID:ArrayList<Int>,id:Int): Int {
        return storeID.indexOf(id)-1
    }
}