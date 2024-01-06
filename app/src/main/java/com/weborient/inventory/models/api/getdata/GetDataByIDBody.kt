package com.weborient.inventory.models.api.getdata

import com.google.gson.annotations.SerializedName

data class GetDataByIDBody(
    @SerializedName("id")
    val id: String
)