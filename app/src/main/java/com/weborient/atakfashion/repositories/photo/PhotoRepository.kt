package com.weborient.atakfashion.repositories.photo

import com.weborient.atakfashion.models.UploadingPhotoModel

object PhotoRepository {
    var itemID: String? = null
    var photoPaths: ArrayList<String> = arrayListOf()

    var uploadingPhotos: ArrayList<UploadingPhotoModel> = arrayListOf()
}