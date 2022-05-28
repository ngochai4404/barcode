package com.khaled.mlbarcodescanner

import android.annotation.SuppressLint
import android.icu.lang.UCharacter
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import com.khaled.mlbarcodescanner.model.Product
import com.khaled.mlbarcodescanner.network.ApiInterface
import com.khaled.mlbarcodescanner.service.BlockchainService
import com.khaled.mlbarcodescanner.utils.PreferenceHelper
import com.khaled.mlbarcodescanner.utils.PreferenceHelper.get
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
        }else{
            llBalance.isGone = false
            llImport.isGone = true
            tvAddress.text = service.getWallet(pk).address;
            tvBalance.text = service.getBalance(service.getWallet(pk).address).toString()+" BNB";
        }
    }
}