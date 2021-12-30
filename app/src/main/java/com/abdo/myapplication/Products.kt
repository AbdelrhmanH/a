package com.abdo.myapplication

import retrofit2.Response
import retrofit2.http.GET

interface Products {

    @GET("/api/vendors/8")
    suspend fun getAll():Response<Details>

}