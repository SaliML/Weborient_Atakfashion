package com.weborient.atakfashion.models.api.common

import com.google.gson.annotations.SerializedName

data class CommonErrorResponse(
    @SerializedName("error")
    val error: String?,

    @SerializedName("params")
    val params: ArrayList<Any>?,

    @SerializedName("logout")
    val logout: Boolean
)
