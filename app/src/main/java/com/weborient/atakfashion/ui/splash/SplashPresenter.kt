package com.weborient.atakfashion.ui.splash

import com.weborient.atakfashion.config.AppConfig
import com.weborient.atakfashion.handlers.dialog.DialogResultEnums
import com.weborient.atakfashion.handlers.dialog.DialogTypeEnums
import com.weborient.atakfashion.handlers.permission.PermissionHandler

class SplashPresenter(private val view: ISplashContract.ISplashView): ISplashContract.ISplashPresenter {
    private val interactor = SplashInteractor(this)

    /**
     * Jogosultságok lekérdezése
     */
    override fun getPermissions() {
        interactor.getPermissions()
    }

    /**
     * MAC és API címek ellenőrzése
     * @param macAddress MAC cím
     * @param apiAddress API cím
     * @param isAutoCut Vágás minden darabnál
     * @param isCutAtEnd Vágás a nyomtatás végén
     */
    override fun onFetchedSettings(ipAddress: String?, apiAddress: String?, isAutoCut: Boolean, isCutAtEnd: Boolean, labelTypeID: Int) {
        if(ipAddress != null && apiAddress != null){
            interactor.setIPAddress(ipAddress)
            interactor.setApiAddress(apiAddress)
            interactor.setCutSettings(isAutoCut, isCutAtEnd)
            interactor.setLabelType(labelTypeID)
            interactor.startTimer(AppConfig.SPLASH_TIMER_DURATION_HOURS, AppConfig.SPLASH_TIMER_DURATION_MINUTES, AppConfig.SPLASH_TIMER_DURATION_SECONDS, AppConfig.SPLASH_TIMER_DOWN_INTERVAL)
        }
        else{
            view.showConfigDialog(ipAddress, apiAddress)
        }
    }

    /**
     * Jogosultságok visszaadása
     * @param permissions Jogosultságtömb
     */
    override fun onFetchedPermissions(permissions: Array<String>) {
        view.checkPermissions(permissions)
    }

    /**
     * Jogosultságok ellenőrzését követően lefutó metódus
     * @param permissions Jogosultságtömb
     */
    override fun onCheckedPermissions(permissions: Array<String>?) {
        if(permissions?.isNotEmpty() == true){
            view.showPermissionDialog(permissions, AppConfig.REQUEST_CODE_PERMISSION)
        }
        else{
            view.getSettings()
        }
    }

    /**
     * Lejárt időzítőhöz tartozó metódus
     */
    override fun onFinishedTimer() {
        view.checkFolders()
        view.readRemovaledProducts()
        view.navigateToMainActivity()
    }

    /**
     * Jogosultságok megadása után lefutó metódus
     * @param requestCode Kérés azonosító
     * @param grantResults Jogosultságok állapotát tartalmazó lista
     */
    override fun onGrantedPermissions(requestCode: Int, grantResults: IntArray) {
        if(requestCode == AppConfig.REQUEST_CODE_PERMISSION){
            if(PermissionHandler.isAllPermissionsGranted(grantResults)){
                view.getSettings()
            }
            else{
                view.showInformationDialog("Nem lett megadva minden szükséges engedély!\nAz alkalmazás be fog zárulni!", DialogTypeEnums.WarningClose)
            }
        }
        else{
            view.showInformationDialog("Hiba történt a jogosultságok megadása során!\nAz alkalmazás be fog zárulni!", DialogTypeEnums.ErrorClose)
        }
    }

    /**
     * Párbeszédablak visszatérő értékének kezelése
     */
    override fun onDialogResult(result: DialogResultEnums) {
        when(result){
            DialogResultEnums.OK->{
                view.closeApplication()
            }
            else->{}
        }
    }
}