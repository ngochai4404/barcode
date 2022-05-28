package com.khaled.mlbarcodescanner.model

import android.graphics.Bitmap
import com.google.gson.annotations.SerializedName
import java.util.*


data class Product(
    @SerializedName("id") var id: String,
                   @SerializedName("code") var valueCode: String,
                   @SerializedName("name") var name: String,
                   @SerializedName("date") var date: String,
                   @SerializedName("format")  val format: String,
                   @SerializedName("price")  val price: Double,
    @SerializedName("image") var Image: String)

