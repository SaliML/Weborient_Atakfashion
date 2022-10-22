package com.weborient.inventory.handlers.permission

import android.content.Context
import android.content.pm.PackageManager

/**
 * Jogosultságok kezeléséért felelős osztály
 */
object PermissionHandler {
    /**
     * A paraméterként átadott jogosultságlistát ellenőrzi és visszaadja azokat a jogosultságokat, amelyeket a felhasználó nem adott (még) meg
     */
    fun getNotGrantedPermissions(context: Context, permissions: Array<String>): Array<String>?{
        val notGrantedPermissions = arrayListOf<String>()

        for(permission in permissions){
            if(context.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED){
                notGrantedPermissions.add(permission)
            }
        }

        if(notGrantedPermissions.isNotEmpty()){
            return notGrantedPermissions.toTypedArray()
        }

        return null
    }

    /**
     * Jogosultságok kérését követően visszadott tömböt vizsgálja, hogy minden jogosultságot megadott-e a felhasználó
     */
    fun isAllPermissionsGranted(grantResults: IntArray): Boolean{
        for(value in grantResults){
            if(value != PackageManager.PERMISSION_GRANTED){
                return false
            }
        }
        return true
    }
}