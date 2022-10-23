package com.weborient.inventory.ui.settings

import android.bluetooth.BluetoothDevice
import com.weborient.inventory.models.PrinterModel

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
        fun showPrinterMacAddress(printerMacAddress: String)

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
         * API elérhetőségének mentése
         */
        fun saveApiAddress(apiAddress: String)

        /**
         * Nyomtató MAC címének mentése
         */
        fun saveMacAddress(macAddress: String)

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
         * Beállított nyomtató lekérdezése
         */
        fun getPrinter()

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
        fun onFetchedMacAddress(macAddress: String)

        /**
         * Beállított nyomtató visszaadása
         */
        fun onFetchedPrinter(printer: PrinterModel?)

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
        fun onClickedApiSaveButton(apiAddress: String)

        /**
         * MAC cím mentés gomb eseménye
         */
        fun onClickedPrinterMacAddressSaveButton(macAddress: String)

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
         * Beállított nyomtató lekérdezése
         */
        fun getPrinter()

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
         * Nyomtató keresése
         */
        fun searchPrinter(pairedDevices: Set<BluetoothDevice>?)
    }
}