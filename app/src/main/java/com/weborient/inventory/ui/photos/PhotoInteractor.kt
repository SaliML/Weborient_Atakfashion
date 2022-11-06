package com.weborient.inventory.ui.photos

import android.provider.ContactsContract.CommonDataKinds.Photo
import com.weborient.inventory.models.PhotoUploadModel
import com.weborient.inventory.repositories.photo.PhotoRepository
import java.io.File

class PhotoInteractor(private val presenter: IPhotosContract.IPhotosPresenter): IPhotosContract.IPhotosInteractor {
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

    override fun uploadPhotos() {
        delete()
        presenter.onRetrievedPhotos(PhotoRepository.photoPaths)
        presenter.onUploadedPhotos(PhotoUploadModel(PhotoRepository.itemID, PhotoRepository.photoPaths))
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

    private fun delete(){
        PhotoRepository.itemID = null
        PhotoRepository.photoPaths.clear()
    }
}