package com.weborient.atakfashion.ui.photos

import android.graphics.Bitmap
import com.weborient.atakfashion.handlers.dialog.DialogResultEnums
import com.weborient.atakfashion.handlers.dialog.DialogTypeEnums
import com.weborient.atakfashion.models.PhotoUploadModel
import com.weborient.atakfashion.models.interfaces.IResponseDialogHandler
import com.weborient.atakfashion.models.photo.PhotoItem

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
        fun showPhotos(photos: ArrayList<PhotoItem>)
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
        fun onRetrievedPhotos(photos: ArrayList<PhotoItem>)
        fun onDeletedPhoto(photoUploadModel: PhotoUploadModel)
        fun onUploadedPhotos(photoUploadModel: PhotoUploadModel, hideQRCode: Boolean)
        fun deletePhoto(path: String?)
        fun setItemID(itemID: String?)
        fun setPhotoUploadModel(photoUploadModel: PhotoUploadModel?)
        fun addPhoto(photo: PhotoItem)
    }

    /**
     * Interactor interfésze
     */
    interface IPhotosInteractor{
        fun setItemID(itemID: String)
        fun setPhotoUploadModel(photoUploadModel: PhotoUploadModel?)
        fun addPhoto(photo: PhotoItem)
        fun uploadPhotos()
        fun deletePhoto(path: String?)
    }
}