package com.weborient.atakfashion.models.api.template

import com.google.gson.annotations.SerializedName

data class TemplateDataArrayElement(
    @SerializedName("id")
    val id: Int,

    @SerializedName("value")
    val value: String
)