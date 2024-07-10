package com.weborient.atakfashion.ui.settings

import android.bluetooth.BluetoothDevice
import com.brother.sdk.lmprinter.setting.QLPrintSettings
import com.weborient.atakfashion.handlers.dialog.DialogTypeEnums
import com.weborient.atakfashion.models.QLPrinterLabelType

/**
 * MVP minta a beállítások felületre
 */
interface ISettingsContract {
    /**
     * View interfésze
     */
    interface ISettingsView{
        /**
         * Szalagméretek beállítása
         */
        fun setLabelSizes(labelSizes: ArrayList<String>)

        /**
         * API cím mutatása
         */
        fun showApiAddress(apiAddress: String)

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
         * Nyomtató szalagtípus azonosítójának mentése
         */
        fun savePrinterLabel(labelID: Int)

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

        /**
         * Mentett szalagméret kiválasztása
         */
        fun selectLabelSize(savedLabelType: QLPrintSettings.LabelSize?, labelTypes: ArrayList<QLPrinterLabelType>)
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
         * Mentett szalagméret lekérdezése
         */
        fun getSavedLabelSize()

        /**
         * API cím visszaadása
         */
        fun onFetchedApiAddress(apiAddress: String)

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
         * Mentett szalagméret visszaadása
         */
        fun onFetchedSavedLabelSize(savedLabelType: QLPrintSettings.LabelSize?, labelTypes: ArrayList<QLPrinterLabelType>)

        /**
         * Vissza gomb eseménye
         */
        fun onClickedBackButton()

        /**
         * API cím mentés gomb eseménye
         */
        fun onClickedApiSaveButton(apiAddress: String?)

        /**
         * Nyomtató beállításai mentése gomb eseménye
         */
        fun onClickedPrinterSettingsSaveButton(ipAddress: String?, isAutoCut: Boolean, isCutAtEnd: Boolean, printerLabelType: QLPrinterLabelType?)

    }

    /**
     * Interactor interfésze
     */
    interface ISettingsInteractor{
        /**
         * Mentett szalagméret lekérdezése
         */
        fun getSavedLabelSize()

        /**
         * API cím lekérdezése
         */
        fun getApiAddress()

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
         * Szalagméret rögzítése
         */
        fun setLabelSize(printerLabelType: QLPrinterLabelType)

        /**
         * API cím beállítása
         */
        fun setApiAddress(apiAddress: String)

        /**
         * Nyomtató IP címének beállítása
         */
        fun setIPAddress(ipAddress: String)

        /**
         * Vágási beállítások rögzítése
         */
        fun setCutSettings(isAutoCut: Boolean, isCutAtEnd: Boolean)
    }
}