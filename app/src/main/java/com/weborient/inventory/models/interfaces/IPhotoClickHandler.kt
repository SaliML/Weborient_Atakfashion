package com.weborient.inventory.models.interfaces

interface IPhotoClickHandler {
    fun onClickedDeletePhotoButton(path: String?)
    fun onClickedPreviewButton(path: String?)
}