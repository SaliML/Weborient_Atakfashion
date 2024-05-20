package com.weborient.atakfashion.models.api.newproduct

import com.google.gson.annotations.SerializedName

data class NewProductGetDataResponse(
    @SerializedName("datas")
    val datas: NewProductGetDataArrays?
)
