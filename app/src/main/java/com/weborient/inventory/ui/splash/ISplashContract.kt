package com.weborient.inventory.ui.splash

import com.weborient.inventory.handlers.dialog.DialogResultEnums
import com.weborient.inventory.handlers.dialog.DialogTypeEnums

/**
 * MVP minta a töltőképernyőre
 */
interface ISplashContract {

    /**
     * View interfésze
     */
    interface ISplashView{
        /**
         * Jogosultságok ellenőrzése
         * @param permissions Jogosultságtömb
         */
        fun checkPermissions(permissions: Array<String>)

        /**
         * Nyomtató MAC címének kinyerése
         */
        fun getSettings()

        /**
         * Navigálás a főképernyőre
         */
        fun navigateToMainActivity()

        /**
         * Alkalmazás bezárása
         */
        fun closeApplication()

        /**
         * Jogosultság ablak megjelenítése
         * @param permissions Jogosultságtömb
         * @param requestCode Kérés azonosító
         */
        fun showPermissionDialog(permissions: Array<String>, requestCode: Int)

        /**
         * Információs ablak megjelenítésének definíciója.
         * @param information párbeszédablakban megjelenítendő információ, String típus.
         * @param type párbeszédablak jellegét meghatározó beállítás, DialogTypeEnums típus.
         */
        fun showInformationDialog(information: String, type: DialogTypeEnums)

        /**
         * Konfig adatok párbeszédablak
         */
        fun showConfigDialog(macAddress: String?, apiAddress: String?)
    }

    /**
     * Presenter interfésze
     */
    interface ISplashPresenter{
        /**
         * Jogosultságok lekérdezése
         */
        fun getPermissions()

        /**
         * MAC és API címek ellenőrzése
         * @param ipAddress IP cím
         * @param apiAddress API cím
         * @param isAutoCut Automatikus vágás minden darabnál
         * @param isCutAtEnd Vágás nyomtatás végén
         * @param labelTypeID Szalagtípus azonosítója
         */
        fun onFetchedSettings(ipAddress: String?, apiAddress: String?, isAutoCut: Boolean, isCutAtEnd: Boolean, labelTypeID: Int)

        /**
         * Jogosultságok visszaadása
         * @param permissions Jogosultságtömb
         */
        fun onFetchedPermissions(permissions: Array<String>)

        /**
         * Jogosultságok ellenőrzését követően lefutó metódus
         * @param permissions Jogosultságtömb
         */
        fun onCheckedPermissions(permissions: Array<String>?)

        /**
         * Lejárt időzítőhöz tartozó metódus
         */
        fun onFinishedTimer()

        /**
         * Jogosultságok megadása után lefutó metódus
         * @param requestCode Kérés azonosító
         * @param grantResults Jogosultságok állapotát tartalmazó lista
         */
        fun onGrantedPermissions(requestCode: Int, grantResults: IntArray)

        /**
         * Párbeszédablak visszatérő értékének kezelése
         */
        fun onDialogResult(result: DialogResultEnums)
    }

    /**
     * Interactor interfésze
     */
    interface ISplashInteractor{
        /**
         * Jogosultságok lekérdezése
         */
        fun getPermissions()

        /**
         * Időzítő indítása
         * @param intervalHours Óra
         * @param intervalMinutes Perc
         * @param intervalSeconds Másodperc
         * @param countDownIntervalInMillis Léptetés milliszekundumban
         */
        fun startTimer(intervalHours: Long, intervalMinutes: Long, intervalSeconds: Long, countDownIntervalInMillis: Long)

        /**
         * Nyomtató MAC címének beállítása
         */
        fun setMacAddress(macAddress: String)

        /**
         * Szalagtípus beállítása
         */
        fun setLabelType(labelTypeID: Int)

        /**
         * Nyomtató IP címének beállítása
         */
        fun setIPAddress(ipAddress: String)

        /**
         * API cím beállítása
         */
        fun setApiAddress(apiAddress: String)

        /**
         * Vágási beállítások rögzítése
         */
        fun setCutSettings(isAutoCut: Boolean, isCutAtEnd: Boolean)
    }

}