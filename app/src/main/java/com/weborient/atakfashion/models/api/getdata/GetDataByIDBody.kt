package com.weborient.atakfashion.models.api.getdata

import com.google.gson.annotations.SerializedName

data class GetDataByIDBody(
    @SerializedName("id")
    val id: String
)