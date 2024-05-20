package com.weborient.atakfashion.repositories.item

import com.weborient.atakfashion.models.api.getdata.ProductData
import com.weborient.atakfashion.models.api.newproduct.ArrayElement

object ItemRepository {
    var products = arrayListOf<ProductData>()

    var selectedProduct: ProductData? = null

    var categories: ArrayList<ArrayElement>? = null
    var templates: ArrayList<ArrayElement>? = null
    var units: ArrayList<ArrayElement>? = null
    var packagetypes: ArrayList<ArrayElement>? = null
    var productstatuses: ArrayList<ArrayElement>? = null
    var taxes: ArrayList<ArrayElement>? = null


}