package com.weborient.inventory.repositories.permission

object PermissionRepository {
    private val permissions = arrayOf(
        android.Manifest.permission.BLUETOOTH,
        android.Manifest.permission.BLUETOOTH_ADMIN,
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION,
        android.Manifest.permission.BLUETOOTH_CONNECT,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    fun getPermissions(): Array<String>{
        return permissions
    }
}