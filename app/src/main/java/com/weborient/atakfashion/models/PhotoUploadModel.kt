package com.weborient.atakfashion.models

import java.io.Serializable

data class PhotoUploadModel(
    val itemID: String?,
    val photosPath: ArrayList<String>
): Serializable