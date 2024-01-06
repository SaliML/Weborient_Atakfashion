package com.weborient.inventory.models.api.modifydata

import com.google.gson.annotations.SerializedName

data class ModifyDataByIDBody(
    @SerializedName("id")
    val id: String?
)
