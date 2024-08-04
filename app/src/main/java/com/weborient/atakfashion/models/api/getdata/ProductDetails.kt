package com.weborient.atakfashion.models.api.getdata

import com.google.gson.annotations.SerializedName
import com.weborient.atakfashion.models.api.template.TemplateData

data class
ProductDetails(
    @SerializedName("id")
    val id: String?,

    @SerializedName("name")
    val name: String?,

    @SerializedName("description")
    val description: String?,

    @SerializedName("categoryId")
    val categoryId: String?,

    @SerializedName("packageTypeId")
    val packageTypeId: String?,

    @SerializedName("taxTypeId")
    val taxTypeId: String?,

    @SerializedName("unitId")
    val unitId: String?,

    @SerializedName("statusId")
    val statusId: String?,

    @SerializedName("templateId")
    val templateId: String?,

    @SerializedName("templatedatas")
    val templatedatas: ArrayList<TemplateData>,

    @SerializedName("quantity")
    val quantity: Int,

    @SerializedName("grossprice")
    val grossprice: Int,
)
