package com.weborient.atakfashion.models.api.quantitychange

import com.google.gson.annotations.SerializedName

/**
 * Mennyiség módosításnál használt hívás
 * Ha negatív a szám, akkor készletcsökkentés történik (ha van megfelelő mennyiség)
 * Ha pozitív a szám, akkor készletnövelés
 */
data class ProductQuantityChangeRequest(
    @SerializedName("id")
    val id: String,

    @SerializedName("quantity")
    val quantity: Int
)
