package com.weborient.atakfashion.models

import com.weborient.atakfashion.models.photo.PhotoItem
import java.io.Serializable

data class PhotoUploadModel(
    val itemID: String?,
    val photos: ArrayList<PhotoItem>
): Serializable