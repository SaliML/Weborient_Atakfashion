package com.weborient.inventory.ui.splash

import com.weborient.inventory.handlers.dialog.DialogTypeEnums

/**
 * MVP minta a töltőképernyőre
 */
interface ISplashContract {

    interface ISplashView{
        fun checkPermissions(permissions: Array<String>)
        fun navigateToMainActivity()
        fun closeApplication()
        fun showPermissionDialog(permissions: Array<String>, requestCode: Int)

        /**
         * Információs ablak megjelenítésének definíciója.
         * @param information párbeszédablakban megjelenítendő információ, String típus.
         * @param type párbeszédablak jellegét meghatározó beállítás, DialogTypeEnums típus.
         */
        fun showInformationDialog(information: String, type: DialogTypeEnums)
    }

    /**
     * Presenter interfésze
     */
    interface ISplashPresenter{
        fun checkPermissions()
        fun getPermissions()
        fun onFetchedPermissions(permissions: Array<String>?)
        fun onFetchedTimerConfig(duration: Long, countDownInterval: Long)
        fun onCheckedPermissions()
        fun onFinishedTimer()
        fun onGrantedPermissions(requestCode: Int, grantResults: IntArray)
    }

    /**
     * Interactor interfésze
     */
    interface ISplashInteractor{
        fun getPermissions()
        fun getTimerConfig()
        fun startTimer(intervalHours: Long, intervalMinutes: Long, intervalSeconds: Long, countDownIntervalInMillis: Long)
    }

}