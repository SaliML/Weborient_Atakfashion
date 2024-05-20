package com.weborient.atakfashion.models.interfaces

interface IResponseDialogHandler {
    fun onSuccessful(information: String)
    fun onFailure(information: String)
}