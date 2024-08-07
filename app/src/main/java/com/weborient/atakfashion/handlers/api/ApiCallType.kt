package com.weborient.womo.handlers.api

enum class ApiCallType(val value: Int) {
    None(0),
    Connection(1),
    Timeout(2),
    Unknown(3),
    NewProductGetData(4),
    NewProductSendData(5),
    AllProducts(6),
    GetOneProduct(7),
    GetOneProductDetails(8),
    AddQuantityToProduct(9),
    SubtractionQuantityFromProduct(10),
    UploadPhoto(11),
    EditProduct(12),
    GetTemplateData(13)
}