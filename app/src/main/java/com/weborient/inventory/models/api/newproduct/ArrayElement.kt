package com.weborient.inventory.models.api.newproduct

import com.google.gson.annotations.SerializedName

class ArrayElement(
    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String,
){
    override fun toString(): String {
        return name
    }
}
