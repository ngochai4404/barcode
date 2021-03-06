package com.khaled.mlbarcodescanner.network

import com.khaled.mlbarcodescanner.model.Order
import com.khaled.mlbarcodescanner.model.Product
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import javax.inject.Singleton
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiInterface {

    @GET("/api/products")
    fun getProduct() : Call<List<Product>>

    @GET("/api/product/{code}")
    fun getProductByCode(@Path("code") code: String) : Call<Product>

    @GET("/api/orders/checking")
    fun order(@Query("tx") tx: String) : Call<Any>

    @GET("/api/orders")
    fun getOrder(@Query("address") address: String) : Call<List<Order>>

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