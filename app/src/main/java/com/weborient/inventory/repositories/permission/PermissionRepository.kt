package com.weborient.inventory.repositories.permission

object PermissionRepository {
    private val permissions = arrayOf(
        android.Manifest.permission.CAMERA,
        android.Manifest.permission.BLUETOOTH_CONNECT
    )

    fun getPermissions(): Array<String>{
        return permissions
    }
}