package com.weborient.inventory.models.api.modifydata

import com.google.gson.annotations.SerializedName

data class ModifyDataByIDResponse(
    @SerializedName("text")
    val text: String?
)
