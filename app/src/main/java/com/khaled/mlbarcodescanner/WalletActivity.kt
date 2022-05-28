package com.khaled.mlbarcodescanner

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.recyclerview.widget.LinearLayoutManager
import com.khaled.mlbarcodescanner.adapter.OrderAdapter
import com.khaled.mlbarcodescanner.model.Order
import com.khaled.mlbarcodescanner.network.ApiInterface
import com.khaled.mlbarcodescanner.service.BlockchainService
import com.khaled.mlbarcodescanner.utils.PreferenceHelper
import com.khaled.mlbarcodescanner.utils.PreferenceHelper.setValue
import kotlinx.android.synthetic.main.activity_product.*
import kotlinx.android.synthetic.main.activity_wallet.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by haipn on 28/05/2022.
 */
class WalletActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallet)
    }
    val service = BlockchainService();
    override fun onResume() {
        super.onResume()
        checkWallet();
        btnImport.setOnClickListener { importKey() }
        btnDelete.setOnClickListener { deleteKey() }
    }

    private fun importKey() {
        PreferenceHelper.defaultPrefs(baseContext).setValue(Constant.WALLET,edtWallet.text.toString());
        checkWallet();
    }
    private fun deleteKey() {
        PreferenceHelper.defaultPrefs(baseContext).setValue(Constant.WALLET,null);
        checkWallet();
    }

    @SuppressLint("SetTextI18n")
    private fun checkWallet() {
        var pk = PreferenceHelper.defaultPrefs(baseContext).getString(Constant.WALLET, null);
        if(pk.isNullOrBlank()){
            llBalance.isGone = true
            llImport.isGone = false
            list_order.isGone = true
        }else{
            llBalance.isGone = false
            llImport.isGone = true
            list_order.isGone = false
            tvAddress.text = service.getWallet(pk).address;
            tvBalance.text = service.getBalance(service.getWallet(pk).address).toString()+" BNB";
        }
        getListOrder();
    }

    private fun getListOrder() {
        var pk = PreferenceHelper.defaultPrefs(baseContext).getString(Constant.WALLET, null);
        if(pk.isNullOrBlank()) {
            return
        }
        val productResult = ApiInterface.create().getOrder(service.getWallet(pk.toString()).address)
        productResult.enqueue( object : Callback<List<Order>> {
            override fun onResponse(call: Call<List<Order>>?, response: Response<List<Order>>?) {
                if(response?.body() != null){
                    list_order.setLayoutManager(LinearLayoutManager(baseContext))
                    var adapter = OrderAdapter(baseContext, response.body())
                    adapter.setClickListener(object : OrderAdapter.ItemClickListener{
                        override fun onItemClick(view: View?, position: Int) {
                            val i = Intent(Intent.ACTION_VIEW)
                            i.data = Uri.parse("https://testnet.bscscan.com/tx/"+response.body()!!.get(position).tx)
                            startActivity(i)
                        }

                    })
                    list_order.setAdapter(adapter)
                    adapter.notifyDataSetChanged()
                }
            }
            override fun onFailure(call: Call<List<Order>>?, t: Throwable?) {
                println("product123"+t!!.localizedMessage!!)
            }
        })
    }
}