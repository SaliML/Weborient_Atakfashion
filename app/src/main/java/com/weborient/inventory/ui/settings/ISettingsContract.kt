package com.weborient.inventory.ui.settings

import android.bluetooth.BluetoothDevice
import com.weborient.inventory.handlers.dialog.DialogTypeEnums

/**
 * MVP minta a beállítások felületre
 */
interface ISettingsContract {
    /**
     * View interfésze
     */
    interface ISettingsView{
        /**
         * API cím mutatása
         */
        fun showApiAddress(apiAddress: String)

        /**
         * Nyomtató MAC címének mutatása
         */
        fun showPrinterMacAddress(printerMacAddress: String?)

        /**
         * Nyomtató IP címének mutatása
         */
        fun showPrinterIPAddress(printerIPAddress: String?)

        /**
         * Nyomtató nevének mutatása
         */
        fun showPrinterName(printerName: String)

        /**
         * Nyomtató párosításának állapota
         */
        fun showPrinterStatus(printerStatus: String)

        /**
         * Alkalmazás verziójának mutatása
         */
        fun showAppVersion(version: String)

        /**
         * API mező hibaüzenet
         */
        fun showApiError(error: String?)

        /**
         * MAC cím mező hibaüzenet
         */
        fun showMacAddressError(error: String?)

        /**
         * IP cím mező hibaüzenet
         */
        fun showIPAddressError(error: String?)

        /**
         * Párbeszédablak megjelenítése
         */
        fun showInformationDialog(information: String, type: DialogTypeEnums)

        /**
         * Vágási beállítások megjelenítése
         */
        fun showCutSettings(isAutoCut: Boolean, isCutAtEnd: Boolean)

        /**
         * API elérhetőségének mentése
         */
        fun saveApiAddress(apiAddress: String)

        /**
         * Nyomtató MAC címének mentése
         */
        fun saveMacAddress(macAddress: String)

        /**
         * Nyomtató IP címének mentése
         */
        fun saveIPAddress(ipAddress: String)

        /**
         * Vágási beállítások mentése
         */
        fun saveCutSettings(isAutoCut: Boolean, isCutAtEnd: Boolean)
        /**
         * Felület bezárása
         */
        fun closeActivity()
    }

    /**
     * Presenter interfésze
     */
    interface ISettingsPresenter{
        /**
         * API cím lekérdezése
         */
        fun getApiAddress()

        /**
         * Nyomtató MAC címének lekérdezése
         */
        fun getMacAddress()

        /**
         * Nyomtató IP címének lekérdezése
         */
        fun getIPAddress()

        /**
         * Vágási beállítások lekérdezése
         */
        fun getCutSettings()

        /**
         * Alkalmazás verziószámának lekérdezése
         */
        fun getAppVersion()

        /**
         * API cím visszaadása
         */
        fun onFetchedApiAddress(apiAddress: String)

        /**
         * Nyomtató MAC címének visszaadása
         */
        fun onFetchedMacAddress(macAddress: String?)

        /**
         * Nyomtató IP címének visszaadása
         */
        fun onFetchedIPAddress(ipAddress: String?)

        /**
         * Vágási beállítások visszaadása
         */
        fun onFetchedCutSettings(isAutoCut: Boolean, isCutAtEnd: Boolean)

        /**
         * Nyomtató nevének visszaadása
         */
        fun onFetchedPrinterName(printerName: String)

        /**
         * Nyomtató párosítási állapota
         */
        fun onFetchedPrinterPairStatus(printerPairStatus: String)

        /**
         * Alkalmazás verziószámának visszaadása
         */
        fun onFetchedAppVersion(version: String)

        /**
         * Vissza gomb eseménye
         */
        fun onClickedBackButton()

        /**
         * API cím mentés gomb eseménye
         */
        fun onClickedApiSaveButton(apiAddress: String?)

        /**
         * MAC cím mentés gomb eseménye
         */
        fun onClickedPrinterSettingsSaveButton(ipAddress: String?, isAutoCut: Boolean, isCutAtEnd: Boolean)

        /**
         * Nyomtató adatainak frissítése
         */
        fun refreshPrinter(pairedDevices: Set<BluetoothDevice>?)
    }

    /**
     * Interactor interfésze
     */
    interface ISettingsInteractor{
        /**
         * API cím lekérdezése
         */
        fun getApiAddress()

        /**
         * Nyomtató MAC címének lekérdezése
         */
        fun getMacAddress()

        /**
         * Nyomtató IP címének lekérdezése
         */
        fun getIPAddress()

        /**
         * Vágási beállítások lekérdezése
         */
        fun getCutSettings()

        /**
         * Alkalmazás verziószámának lekérdezése
         */
        fun getAppVersion()

        /**
         * API cím beállítása
         */
        fun setApiAddress(apiAddress: String)

        /**
         * Nyomtató MAC címének beállítása
         */
        fun setMacAddress(macAddress: String)

        /**
         * Nyomtató IP címének beállítása
         */
        fun setIPAddress(ipAddress: String)

        /**
         * Vágási beállítások rögzítése
         */
        fun setCutSettings(isAutoCut: Boolean, isCutAtEnd: Boolean)

        /**
         * Nyomtató keresése
         */
        fun searchPrinter(pairedDevices: Set<BluetoothDevice>?)
    }
}