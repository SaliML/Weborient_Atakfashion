package com.weborient.atakfashion.models.removal

import com.weborient.atakfashion.models.api.getdata.ProductData
import java.util.Date

data class RemovaledGroup(
    val date: Date,
    var products: ArrayList<ProductData>
)
