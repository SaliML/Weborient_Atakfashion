package com.weborient.atakfashion.ui.photos

import com.weborient.atakfashion.config.AppConfig
import com.weborient.atakfashion.handlers.api.ApiCallResponse
import com.weborient.atakfashion.handlers.api.ApiServiceHandler
import com.weborient.atakfashion.handlers.api.ServiceBuilder
import com.weborient.atakfashion.models.PhotoUploadModel
import com.weborient.atakfashion.models.UploadingPhotoModel
import com.weborient.atakfashion.models.photo.PhotoItem
import com.weborient.atakfashion.repositories.photo.PhotoRepository
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
            PhotoRepository.photos = it.photos

            presenter.onRetrievedPhotos(PhotoRepository.photos)
        }
    }

    override fun addPhoto(photo: PhotoItem) {
        PhotoRepository.photos.add(photo)
        presenter.onRetrievedPhotos(PhotoRepository.photos)
        presenter.onAddedPhoto(PhotoUploadModel(PhotoRepository.itemID, PhotoRepository.photos))
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
            PhotoRepository.photos.forEach { photo ->
                val photo = File(photo.path)

                if(photo.exists()){
                    PhotoRepository.itemID?.let{ productID ->
                        //Feltöltési állapot hozzáadása
                        PhotoRepository.uploadingPhotos.add(UploadingPhotoModel(photo.path, null))

                        //Body->form-data összeállítása
                        val idBody = RequestBody.create(MediaType.parse("text/plain"), productID)
                        val fileBody = MultipartBody.Part.createFormData("file", photo.name, RequestBody.create(MediaType.parse("image/*"), photo))

                        //Hívás
                        ApiServiceHandler.apiService(service.callAddImageSendData(idBody, fileBody), ApiCallType.UploadPhoto, this, photo.path)
                    }
                }
            }
        }
    }

    override fun deletePhoto(path: String?) {
        if(path != null){
            val photo = PhotoRepository.photos.firstOrNull { it.path.equals(path, true) }

            photo?.let{ photoItem ->
                if(photoItem.canDelete){
                    if(File(photoItem.path).delete()){
                        PhotoRepository.photos.remove(photoItem)
                        presenter.onRetrievedPhotos(PhotoRepository.photos)
                        presenter.onDeletedPhoto(PhotoUploadModel(PhotoRepository.itemID, PhotoRepository.photos))
                    }
                }
                else{
                    PhotoRepository.photos.remove(photoItem)
                    presenter.onRetrievedPhotos(PhotoRepository.photos)
                    presenter.onDeletedPhoto(PhotoUploadModel(PhotoRepository.itemID, PhotoRepository.photos))
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
        PhotoRepository.photos.clear()
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
                presenter.onUploadedPhotos(PhotoUploadModel(PhotoRepository.itemID, PhotoRepository.photos), true)

                //Képek visszaadása
                presenter.onRetrievedPhotos(PhotoRepository.photos)

                presenter.onSuccessful("Fényképek feltöltve!")
            }
        }
    }

    override fun onResult(callResponse: ApiCallResponse) {
        if(callResponse.isSuccessful){
            //Egyelőre felesleges ellenőrizni, mert csak fotó feltöltés megy
            PhotoRepository.uploadingPhotos.firstOrNull { it.photoPath.equals(callResponse.param, true) }?.let {
                it.isSuccess = true
            }

            checkUploadingPhotosStatus()
        }
        else{
            //Függetlenül a hibától, sikertelenre tesszük a feltöltést
            PhotoRepository.uploadingPhotos.firstOrNull { it.photoPath.equals(callResponse.param, true) }?.let {
                it.isSuccess = false
            }

            checkUploadingPhotosStatus()
        }
    }
}