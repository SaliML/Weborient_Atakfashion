package com.weborient.atakfashion.models

data class ItemModel(
    val id: String,
    val name: String,
    val description: String,
    val photoURL: String,
    var isSelected: Boolean
)
