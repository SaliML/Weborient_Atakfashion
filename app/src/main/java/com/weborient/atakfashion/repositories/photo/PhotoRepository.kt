package com.weborient.atakfashion.repositories.photo

import com.weborient.atakfashion.models.UploadingPhotoModel
import com.weborient.atakfashion.models.photo.PhotoItem

object PhotoRepository {
    var itemID: String? = null
    var photos: ArrayList<PhotoItem> = arrayListOf()

    var uploadingPhotos: ArrayList<UploadingPhotoModel> = arrayListOf()
}