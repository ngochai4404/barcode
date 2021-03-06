package com.khaled.mlbarcodescanner

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.khaled.mlbarcodescanner.model.Product
import com.khaled.mlbarcodescanner.network.ApiInterface
import com.khaled.mlbarcodescanner.service.BlockchainService
import com.khaled.mlbarcodescanner.utils.PreferenceHelper
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_product.*
import kotlinx.android.synthetic.main.activity_wallet.*
import kotlinx.android.synthetic.main.product_detail.*
import kotlinx.android.synthetic.main.product_detail.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by haipn on 28/05/2022.
 */
class ProductDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.product_detail)
    }
    val service = BlockchainService();
    val code get() = intent.getStringExtra("code");
    var product: Product? = null
    override fun onResume() {
        super.onResume()
        getData();
        btnBuy.setOnClickListener{buyItem()}
    }

    private fun buyItem() {
        var pk = PreferenceHelper.defaultPrefs(baseContext).getString(Constant.WALLET,null);
        product?.let{
            var data = service.buyItem(Integer.parseInt(it.id),1,it.price, service.getWallet(pk));
            var hash = data.transactionHash
            Toast.makeText(baseContext,hash,Toast.LENGTH_SHORT).show()
            Thread.sleep(7000);
            order(hash);

        }
    }


    private fun getData() {
        val productResult = ApiInterface.create().getProductByCode(code?:"")
        productResult.enqueue( object : Callback<Product> {
            override fun onResponse(call: Call<Product>?, response: Response<Product>?) {
                if(response?.body() != null){
                    product = response.body()!!
                    id_products.text = "ID: "+product!!.id;
                    name_products.text = "Name: "+product!!.name;
                    code_products.text = "Code: "+product!!.valueCode;
                    date_products.text = "Price: "+product!!.price.toString()+" BNB";
                    format_products.text = "Amount" +
                            ": "+product!!.amount.toString();
                    Picasso.get().load(product!!.Image)
                        .into(image_products)
                }
            }
            override fun onFailure(call: Call<Product>?, t: Throwable?) {
                println("product123"+t!!.localizedMessage!!)
            }
        })
    }

    private fun order(hash: String) {
        val productResult = ApiInterface.create().order(hash)
        productResult.enqueue( object : Callback<Any> {
            override fun onResponse(call: Call<Any>?, response: Response<Any>?) {
                if(response?.body() != null){
                    Toast.makeText(baseContext,response.body().toString(),Toast.LENGTH_SHORT).show()
                    getData()

                }
            }
            override fun onFailure(call: Call<Any>?, t: Throwable?) {
                println("product123"+t!!.localizedMessage!!)
            }
        })
    }
}