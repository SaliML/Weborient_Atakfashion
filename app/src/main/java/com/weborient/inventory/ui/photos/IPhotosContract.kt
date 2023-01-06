package com.weborient.inventory.ui.photos

import android.graphics.Bitmap
import com.weborient.inventory.handlers.dialog.DialogResultEnums
import com.weborient.inventory.handlers.dialog.DialogTypeEnums
import com.weborient.inventory.models.PhotoUploadModel
import com.weborient.inventory.models.interfaces.IResponseDialogHandler

/**
 * MVP minta a fényképek felülethez
 */
interface IPhotosContract {
    /**
     * View interfésze
     */
    interface IPhotosView{
        fun showQRCode(bitmap: Bitmap)
        fun hideQRCode()
        fun showInformationDialog(information: String, type: DialogTypeEnums)
        fun showNetworkDialog()
        fun showPhotos(photoPaths: ArrayList<String>)
        fun showProgressDialog()
        fun hideProgressDialog()
        fun navigateToScannerActivity()
        fun navigateToCameraActivity()
        fun save(photoUploadModel: PhotoUploadModel)
        fun deleteFiles()
        fun closeActivity()
    }

    /**
     * Presenter interfésze
     */
    interface IPhotosPresenter: IResponseDialogHandler {
        fun onClickedBackButton()
        fun onClickedTakePhotoButton()
        fun onClickedScanButton()
        fun onClickedUploadButton()
        fun onDialogResult(result: DialogResultEnums)
        fun onAddedPhoto(photoUploadModel: PhotoUploadModel)
        fun onRetrievedPhotos(photoPaths: ArrayList<String>)
        fun onDeletedPhoto(photoUploadModel: PhotoUploadModel)
        fun onUploadedPhotos(photoUploadModel: PhotoUploadModel, hideQRCode: Boolean)
        fun deletePhoto(path: String?)
        fun setItemID(itemID: String?)
        fun setPhotoUploadModel(photoUploadModel: PhotoUploadModel?)
        fun addPhoto(photoPath: String)
    }

    /**
     * Interactor interfésze
     */
    interface IPhotosInteractor{
        fun setItemID(itemID: String)
        fun setPhotoUploadModel(photoUploadModel: PhotoUploadModel?)
        fun addPhoto(photoPath: String)
        fun uploadPhotos()
        fun deletePhoto(path: String?)
    }
}