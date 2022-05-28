package com.khaled.mlbarcodescanner.network

import com.khaled.mlbarcodescanner.model.Product
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import javax.inject.Singleton
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Path


interface ApiInterface {

    @GET("/api/products")
    fun getProduct() : Call<List<Product>>

    @GET("/api/product/{code}")
    fun getProductByCode(@Path("code") code: String) : Call<Product>

    companion object {
        @Singleton
        var BASE_URL = "http://192.168.1.8:3000"

        fun create() : ApiInterface {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiInterface::class.java)

        }
    }
    //
}