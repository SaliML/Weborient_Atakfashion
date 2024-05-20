package com.weborient.atakfashion.ui.photos

import android.content.res.Resources
import com.weborient.atakfashion.R
import com.weborient.atakfashion.handlers.dialog.DialogResultEnums
import com.weborient.atakfashion.handlers.dialog.DialogTypeEnums
import com.weborient.atakfashion.handlers.qrcode.QRCodeHandler
import com.weborient.atakfashion.models.PhotoUploadModel
import com.weborient.atakfashion.repositories.photo.PhotoRepository

class PhotoPresenter(private val view: IPhotosContract.IPhotosView): IPhotosContract.IPhotosPresenter {
    private val interactor = PhotoInteractor(this)

    override fun onClickedBackButton() {
        view.closeActivity()
    }

    override fun onClickedTakePhotoButton() {
        view.navigateToCameraActivity()
    }

    override fun onClickedScanButton() {
        view.navigateToScannerActivity()
    }

    override fun onClickedUploadButton() {
        if(PhotoRepository.photoPaths.size == 0){
            view.showInformationDialog("Kérem készítsen képeket a feltöltéshez!", DialogTypeEnums.Warning)
        }
        else{
            if(PhotoRepository.itemID.isNullOrEmpty()){
                view.showInformationDialog("Kérem olvassa be a QR kódot a feltöltéshez!", DialogTypeEnums.Warning)
            }
            else{
                view.showProgressDialog()
                interactor.uploadPhotos()
            }
        }
    }

    override fun onDialogResult(result: DialogResultEnums) {
        when(result){
            DialogResultEnums.SettingsNetwork->{
                view.showNetworkDialog()
            }
            else->{}
        }
    }

    override fun onAddedPhoto(photoUploadModel: PhotoUploadModel) {
        view.save(photoUploadModel)
    }

    override fun onRetrievedPhotos(photoPaths: ArrayList<String>) {
        view.showPhotos(photoPaths)
    }

    override fun onDeletedPhoto(photoUploadModel: PhotoUploadModel) {
        view.save(photoUploadModel)
    }

    override fun onUploadedPhotos(photoUploadModel: PhotoUploadModel, hideQRCode: Boolean) {
        view.save(photoUploadModel)

        if(hideQRCode){
            view.hideQRCode()
        }
    }

    override fun deletePhoto(path: String?) {
        interactor.deletePhoto(path)
    }

    override fun setItemID(itemID: String?) {
        if(itemID.isNullOrEmpty()){
            view.hideQRCode()
            view.showInformationDialog(Resources.getSystem().getString(R.string.photos_qr_code_scan_error), DialogTypeEnums.Error)
        }
        else{
            interactor.setItemID(itemID)
            view.showQRCode(QRCodeHandler.generateQRCode(itemID))
            view.save(PhotoUploadModel(PhotoRepository.itemID, PhotoRepository.photoPaths))
        }
    }

    override fun setPhotoUploadModel(photoUploadModel: PhotoUploadModel?) {
        photoUploadModel?.itemID?.let{
            view.showQRCode(QRCodeHandler.generateQRCode(it))
        }

        interactor.setPhotoUploadModel(photoUploadModel)
    }

    override fun addPhoto(photoPath: String) {
        interactor.addPhoto(photoPath)
    }

    override fun onSuccessful(information: String) {
        view.hideProgressDialog()
        view.deleteFiles()
        view.showInformationDialog(information, DialogTypeEnums.Successful)
    }

    override fun onFailure(information: String) {
        view.hideProgressDialog()
        view.showInformationDialog(information, DialogTypeEnums.Error)
    }
}