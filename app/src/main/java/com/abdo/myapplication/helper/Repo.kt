package com.abdo.myapplication.helper

import android.util.Log
import com.abdo.myapplication.Details
import com.abdo.myapplication.Products

class Repo(
    private val products: Products
): BaseDataSource() {

    suspend fun getProducts() = getResult { products.getAll() }

}