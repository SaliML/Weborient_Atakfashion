package com.weborient.atakfashion.models.api.template

import com.google.gson.annotations.SerializedName

data class TemplateDataBase(
    @SerializedName("datas")
    val datas: TemplateDatas
)
