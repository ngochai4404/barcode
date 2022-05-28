package com.khaled.mlbarcodescanner.model

import android.graphics.Bitmap
import com.google.gson.annotations.SerializedName
import java.util.*


data class Order(
    @SerializedName("id") var id: String,
   @SerializedName("name") var name: String,
   @SerializedName("created_at") var date: String,
   @SerializedName("tx")  val tx: String,
    @SerializedName("image") var Image: String)

