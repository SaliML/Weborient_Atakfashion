package com.weborient.atakfashion.models.api.template

import com.google.gson.annotations.SerializedName

data class TemplateSendData(
    @SerializedName("propertyId")
    val propertyId: String,

    @SerializedName("values")
    val values: ArrayList<String>
)
