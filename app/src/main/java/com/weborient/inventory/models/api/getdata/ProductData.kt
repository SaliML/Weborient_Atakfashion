package com.weborient.inventory.models.api.getdata

import com.google.gson.annotations.SerializedName

data class ProductData(
    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("quantity")
    val quantity: Int,

    @SerializedName("categoryname")
    val categoryName: String,

    @SerializedName("pictureurl")
    val pictureURL: String?
)
