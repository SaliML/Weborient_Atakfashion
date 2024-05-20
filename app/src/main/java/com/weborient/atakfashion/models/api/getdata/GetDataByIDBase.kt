package com.weborient.atakfashion.models.api.getdata

import com.google.gson.annotations.SerializedName

data class GetDataByIDBase(
    @SerializedName("datas")
    val datas: ArrayList<ProductDetails>,

    @SerializedName("text")
    val text: String?
)
