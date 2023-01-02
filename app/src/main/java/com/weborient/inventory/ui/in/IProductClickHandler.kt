package com.weborient.inventory.ui.`in`

import com.weborient.inventory.models.api.getdata.ProductData

interface IProductClickHandler {
    fun onClickedProduct(product: ProductData?)
}