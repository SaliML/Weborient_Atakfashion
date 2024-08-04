package com.weborient.atakfashion.models.api.getdata

import com.google.gson.annotations.SerializedName
import com.weborient.atakfashion.models.api.template.TemplateData

data class ProductData(
    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("quantity")
    var quantity: Int,

    @SerializedName("categoryname")
    val categoryName: String,

    @SerializedName("pictureurl")
    val pictureURL: String?,

    @SerializedName("grossprice")
    val grossprice: Int,

    @SerializedName("template")
    val template: String?,

    @SerializedName("templatedatas")
    val templatedatas: ArrayList<TemplateData>,

    @SerializedName("isSelected")
    var isSelected: Boolean = false
)
