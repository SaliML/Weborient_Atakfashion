package com.weborient.atakfashion.models.api.template

import com.google.gson.annotations.SerializedName

data class TemplateDatas(
    @SerializedName("templatedatas")
    val templatedatas: ArrayList<TemplateData>
)
