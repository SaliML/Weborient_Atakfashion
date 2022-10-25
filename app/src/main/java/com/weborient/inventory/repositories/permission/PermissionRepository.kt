package com.weborient.inventory.repositories.permission

import android.os.Build.VERSION_CODES
import com.weborient.inventory.models.PermissionModel

object PermissionRepository {
    private val permissions = arrayListOf(
        PermissionModel(android.Manifest.permission.CAMERA, VERSION_CODES.BASE),
        PermissionModel(android.Manifest.permission.BLUETOOTH, VERSION_CODES.BASE),
        PermissionModel(android.Manifest.permission.BLUETOOTH_ADMIN, VERSION_CODES.BASE),
        PermissionModel(android.Manifest.permission.INTERNET, VERSION_CODES.BASE),
        PermissionModel(android.Manifest.permission.ACCESS_NETWORK_STATE, VERSION_CODES.BASE),
        PermissionModel(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, VERSION_CODES.BASE),
        PermissionModel(android.Manifest.permission.BLUETOOTH_CONNECT, VERSION_CODES.S)
    )

    fun getPermissions(apiVersionCode: Int): Array<String>{
        val permissionList = permissions.filter { apiVersionCode >= it.minSDKVersionCode }
        val permissionNameList = arrayListOf<String>()

        permissionList.forEach { permissionNameList.add(it.permission) }

        return permissionNameList.toTypedArray()
    }
}