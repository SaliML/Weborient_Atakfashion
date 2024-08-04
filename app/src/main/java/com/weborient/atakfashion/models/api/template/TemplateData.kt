package com.weborient.atakfashion.models.api.template

import com.google.gson.annotations.SerializedName
import com.weborient.atakfashion.models.api.newproduct.ArrayElement

data class TemplateData(
    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("data")
    val data: ArrayList<TemplateDataArrayElement>?,

    @SerializedName("selecteddata")
    var selecteddata: ArrayList<Int>?
)
