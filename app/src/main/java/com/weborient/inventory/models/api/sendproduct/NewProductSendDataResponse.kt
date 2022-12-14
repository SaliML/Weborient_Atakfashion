package com.weborient.inventory.models.api.sendproduct

import com.google.gson.annotations.SerializedName

data class NewProductSendDataResponse(
    @SerializedName("id")
    val id: String?
)
