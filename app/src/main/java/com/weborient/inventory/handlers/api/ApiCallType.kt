package com.weborient.womo.handlers.api

enum class ApiCallType(val value: Int) {
    None(0),
    Connection(1),
    Timeout(2),
    Unknown(3),
    NewProductGetData(4),
    NewProductSendData(5),
    AllProducts(6),
    GetOneProduct(7)
}