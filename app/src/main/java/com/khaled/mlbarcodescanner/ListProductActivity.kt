package com.khaled.mlbarcodescanner

import android.icu.lang.UCharacter
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.khaled.mlbarcodescanner.model.Product
import com.khaled.mlbarcodescanner.network.ApiInterface
import kotlinx.android.synthetic.main.activity_product.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by haipn on 28/05/2022.
 */
class ListProductActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)
    }

    override fun onResume() {
        super.onResume()
        getListProduct();
    }

    private fun getListProduct() {
        val productResult = ApiInterface.create().getProduct()
        productResult.enqueue( object : Callback<List<Product>> {
            override fun onResponse(call: Call<List<Product>>?, response: Response<List<Product>>?) {
                if(response?.body() != null){
                    // TODO set data on rcv
                    println(response.body().toString());
                    tvTest.text = response.body().toString()
                }
            }
            override fun onFailure(call: Call<List<Product>>?, t: Throwable?) {
                println("product123"+t!!.localizedMessage!!)
            }
        })
    }
}