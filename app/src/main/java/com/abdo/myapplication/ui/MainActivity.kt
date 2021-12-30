package com.abdo.myapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdo.myapplication.*
import com.abdo.myapplication.databinding.ActivityMainBinding
import com.abdo.myapplication.helper.Repo
import com.abdo.myapplication.helper.Resource
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    companion object{
        val TAG = MainActivity::class.java.simpleName
    }

    private lateinit var binding: ActivityMainBinding
    private val viewModel : MainActivityVM by viewModels()

    private lateinit var okHttpClient: OkHttpClient
    private lateinit var retrofit: Retrofit
    private lateinit var json: Gson
    private lateinit var moduleProduct: Details

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        init()

    }

    private fun init(){

        lifecycleScope.launch(Dispatchers.IO){
            json = GsonBuilder().setLenient().create()
            okHttpClient = OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }).build()
            retrofit = Retrofit.Builder()
                .baseUrl(Const.URL_API_BASIC)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()

            val products = retrofit.create(Products::class.java)

            val result = Repo(products).getProducts()
            when (result.status) {
                Resource.Status.SUCCESS -> {
                    moduleProduct = result.data!!
                    if (moduleProduct.success == true){
                        withContext(Dispatchers.Main){
                            createModuleDynamic(moduleProduct.data)
                        }
                    }
                }
                Resource.Status.ERROR -> {
                    val m = result.message
                    Log.d("TAG_All", "getProducts:ERROR $m")
                }
                Resource.Status.LOADING -> {
                    val m = result.message
                    Log.d("TAG_All", "getProducts:LOADING $m")
                }
            }

        }

    }

    private fun createModuleDynamic(data: Data?) {
        binding.rvCategory.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = data?.let { QuickLookAtSectionAdapter(context, it) }
            setHasFixedSize(true)
        }
    }
}