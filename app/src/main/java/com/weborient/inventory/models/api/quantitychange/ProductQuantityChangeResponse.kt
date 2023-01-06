package com.weborient.inventory.models.api.quantitychange

import com.google.gson.annotations.SerializedName

/**
 * Mennyiség módosítása esetén visszaérkező válasz
 */
data class ProductQuantityChangeResponse(
    @SerializedName("id")
    val id: String,

    @SerializedName("quantity")
    val quantity: Int,

    @SerializedName("text")
    val text: String?
)
