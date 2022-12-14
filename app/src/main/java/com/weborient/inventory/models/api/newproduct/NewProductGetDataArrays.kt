package com.weborient.inventory.models.api.newproduct

import com.google.gson.annotations.SerializedName

data class NewProductGetDataArrays(
    @SerializedName("categories")
    val categories: ArrayList<ArrayElement>?,

    @SerializedName("templates")
    val templates: ArrayList<ArrayElement>?,

    @SerializedName("units")
    val units: ArrayList<ArrayElement>?,

    @SerializedName("packagetypes")
    val packagetypes: ArrayList<ArrayElement>?,

    @SerializedName("productstatuses")
    val productstatuses: ArrayList<ArrayElement>?,

    @SerializedName("taxes")
    val taxes: ArrayList<ArrayElement>?
)
