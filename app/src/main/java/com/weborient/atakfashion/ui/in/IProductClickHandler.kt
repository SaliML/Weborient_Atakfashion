package com.weborient.atakfashion.ui.`in`

import com.weborient.atakfashion.models.api.getdata.ProductData

interface IProductClickHandler {
    fun onClickedProduct(product: ProductData?)
}