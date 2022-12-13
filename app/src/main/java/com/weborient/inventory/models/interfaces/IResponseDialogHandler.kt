package com.weborient.inventory.models.interfaces

interface IResponseDialogHandler {
    fun onSuccessful(information: String)
    fun onFailure(information: String)
}