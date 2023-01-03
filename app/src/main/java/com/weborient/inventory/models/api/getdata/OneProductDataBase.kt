package com.weborient.inventory.models.api.getdata

import com.google.gson.annotations.SerializedName

data class OneProductDataBase(
    @SerializedName("datas")
    val datas: ProductData
)
