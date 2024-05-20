package com.weborient.atakfashion.models.api.getdata

import com.google.gson.annotations.SerializedName

data class ProductDataBase(
    @SerializedName("datas")
    val datas: ArrayList<ProductData>
)
