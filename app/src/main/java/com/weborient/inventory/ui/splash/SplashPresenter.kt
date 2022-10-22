package com.weborient.inventory.ui.splash

import com.weborient.inventory.config.AppConfig
import com.weborient.inventory.handlers.dialog.DialogTypeEnums
import com.weborient.inventory.handlers.permission.PermissionHandler

class SplashPresenter(private val view: ISplashContract.ISplashView): ISplashContract.ISplashPresenter {
    private val interactor = SplashInteractor(this)

    override fun checkPermissions() {
        interactor.
    }

    override fun getPermissions() {
        TODO("Not yet implemented")
    }

    override fun onFetchedPermissions(permissions: Array<String>?) {
        if(permissions?.isNotEmpty() == true){
            view.showPermissionDialog(permissions, AppConfig.REQUEST_CODE_PERMISSION)
        }
    }


    override fun onFetchedTimerConfig(duration: Long, countDownInterval: Long) {
        TODO("Not yet implemented")
    }

    override fun onCheckedPermissions() {
        TODO("Not yet implemented")
    }

    override fun onFinishedTimer() {
        view.navigateToMainActivity()
    }

    override fun onGrantedPermissions(requestCode: Int, grantResults: IntArray) {
        if(requestCode == AppConfig.REQUEST_CODE_PERMISSION){
            if(PermissionHandler.isAllPermissionsGranted(grantResults)){
                interactor.startTimer(AppConfig.SPLASH_TIMER_DURATION_HOURS, AppConfig.SPLASH_TIMER_DURATION_MINUTES, AppConfig.SPLASH_TIMER_DURATION_SECONDS, AppConfig.SPLASH_TIMER_DOWN_INTERVAL)
            }
            else{
                view.showInformationDialog("Nem lett megadva minden szükséges engedély!\nAz alkalmazás be fog zárulni!", DialogTypeEnums.Warning)
            }
        }
        else{
            view.showInformationDialog("Hiba történt a jogosultságok megadása során!\nAz alkalmazás be fog zárulni!", DialogTypeEnums.Error)
        }
    }
}