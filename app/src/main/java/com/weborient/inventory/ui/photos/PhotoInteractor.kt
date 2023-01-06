package com.weborient.inventory.ui.photos

import com.weborient.inventory.config.AppConfig
import com.weborient.inventory.handlers.api.ApiServiceHandler
import com.weborient.inventory.handlers.api.ServiceBuilder
import com.weborient.inventory.models.PhotoUploadModel
import com.weborient.inventory.models.UploadingPhotoModel
import com.weborient.inventory.repositories.photo.PhotoRepository
import com.weborient.womo.handlers.api.ApiCallType
import com.weborient.womo.handlers.api.IApiResponseHandler
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class PhotoInteractor(private val presenter: IPhotosContract.IPhotosPresenter): IPhotosContract.IPhotosInteractor,
    IApiResponseHandler {
    override fun setItemID(itemID: String) {
        PhotoRepository.itemID = itemID
    }

    override fun setPhotoUploadModel(photoUploadModel: PhotoUploadModel?) {
        photoUploadModel?.let{
            PhotoRepository.itemID = it.itemID
            PhotoRepository.photoPaths = it.photosPath

            presenter.onRetrievedPhotos(PhotoRepository.photoPaths)
        }
    }

    override fun addPhoto(photoPath: String) {
        PhotoRepository.photoPaths.add(photoPath)
        presenter.onRetrievedPhotos(PhotoRepository.photoPaths)
        presenter.onAddedPhoto(PhotoUploadModel(PhotoRepository.itemID, PhotoRepository.photoPaths))
    }

    /**
     * Fényképek feltöltése
     */
    override fun uploadPhotos() {
        ServiceBuilder.createServiceWithoutBearer()

        AppConfig.apiServiceWithoutBearer?.let{ service ->
            //Feltöltés alatt álló képek törlése
            PhotoRepository.uploadingPhotos.clear()

            //Képek feltöltése egyesével
            PhotoRepository.photoPaths.forEach { photoPath ->
                val photo = File(photoPath)

                if(photo.exists()){
                    PhotoRepository.itemID?.let{ productID ->
                        //Feltöltési állapot hozzáadása
                        PhotoRepository.uploadingPhotos.add(UploadingPhotoModel(photoPath, null))

                        //Body->form-data összeállítása
                        val idBody = RequestBody.create(MediaType.parse("text/plain"), productID)
                        val fileBody = MultipartBody.Part.createFormData("file", photo.name, RequestBody.create(MediaType.parse("image/*"), photo))

                        //Hívás
                        ApiServiceHandler.apiService(service.callAddImageSendData(idBody, fileBody), ApiCallType.UploadPhoto, this, photoPath)
                    }
                }
            }
        }
    }

    override fun deletePhoto(path: String?) {
        if(path != null){
            val photoPath = PhotoRepository.photoPaths.firstOrNull { it.equals(path, true) }

            photoPath?.let{
                if(File(it).delete()){
                    PhotoRepository.photoPaths.remove(it)
                    presenter.onRetrievedPhotos(PhotoRepository.photoPaths)
                    presenter.onDeletedPhoto(PhotoUploadModel(PhotoRepository.itemID, PhotoRepository.photoPaths))
                }
            }
        }
    }

    /**
     * Minden fotó és termékazonosító törlése
     */
    private fun delete(){
        //Termékazonosító törlése
        PhotoRepository.itemID = null

        //Fájlútvonalak törlése
        PhotoRepository.photoPaths.clear()
    }

    /**
     * Sikeresen feltöltött fényképek törlése
     */
    private fun deleteUploadedPhotos(){
        PhotoRepository.uploadingPhotos.forEach { uploadingPhoto ->
            if(uploadingPhoto.isSuccess == true){
                //Fájl törlése
                deletePhoto(uploadingPhoto.photoPath)
            }
        }
    }


    override fun onSuccessful(responseType: ApiCallType, result: Any?, param: String?) {
        //Egyelőre felesleges ellenőrizni, mert csak fotó feltöltés megy
        PhotoRepository.uploadingPhotos.firstOrNull { it.photoPath.equals(param, true) }?.let {
            it.isSuccess = true
        }

        checkUploadingPhotosStatus()
    }

    override fun onFailure(responseType: ApiCallType, result: Any?, throwable: Throwable?, param: String?) {
        //Függetlenül a hibától, sikertelenre tesszük a feltöltést
        PhotoRepository.uploadingPhotos.firstOrNull { it.photoPath.equals(param, true) }?.let {
            it.isSuccess = false
        }

        checkUploadingPhotosStatus()
    }

    /**
     * Fényképek feltöltésének állapota
     */
    private fun checkUploadingPhotosStatus(){
        //Ha már nincs olyan kép, aminek nincs eredménye, ekkor adunk visszajelzést
        if(!PhotoRepository.uploadingPhotos.any { it.isSuccess == null }){
            if(PhotoRepository.uploadingPhotos.any { it.isSuccess == false }){
                deleteUploadedPhotos()

                presenter.onFailure("Nem minden kép feltöltése sikerült!")
            }
            else{
                delete()

                //Állapot mentése
                presenter.onUploadedPhotos(PhotoUploadModel(PhotoRepository.itemID, PhotoRepository.photoPaths), true)

                //Képek visszaadása
                presenter.onRetrievedPhotos(PhotoRepository.photoPaths)

                presenter.onSuccessful("Fényképek feltöltve!")
            }
        }
    }
}