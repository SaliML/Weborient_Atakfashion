package com.weborient.inventory.repositories.item

import com.weborient.inventory.models.ItemModel
import com.weborient.inventory.models.api.getdata.ProductData
import com.weborient.inventory.models.api.newproduct.ArrayElement

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