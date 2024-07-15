package com.weborient.atakfashion.models.api.template

import com.google.gson.annotations.SerializedName

data class TemplateFilter(
    @SerializedName("template")
    val template: String,
)
