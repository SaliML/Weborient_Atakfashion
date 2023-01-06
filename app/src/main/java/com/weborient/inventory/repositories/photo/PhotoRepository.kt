package com.weborient.inventory.repositories.photo

import com.weborient.inventory.models.UploadingPhotoModel

object PhotoRepository {
    var itemID: String? = null
    var photoPaths: ArrayList<String> = arrayListOf()

    var uploadingPhotos: ArrayList<UploadingPhotoModel> = arrayListOf()
}