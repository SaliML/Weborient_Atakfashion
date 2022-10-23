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
        fun showApiAddress(apiAddress: String)
        fun showPrinterMacAddress(printerMacAddress: String)
        fun showPrinterName(printerName: String)
        fun showPrinterStatus(printerStatus: String)
        fun showAppVersion(version: String)
        fun saveApiAddress(apiAddress: String)
        fun saveMacAddress(macAddress: String)
        fun closeActivity()
    }

    /**
     * Presenter interfésze
     */
    interface ISettingsPresenter{
        fun getApiAddress()
        fun getPrinter()
        fun getAppVersion()
        fun onFetchedApiAddress(apiAddress: String)
        fun onFetchedPrinter(printer: PrinterModel?)
        fun onFetchedAppVersion(version: String)
        fun onCLickedRefreshPrinterButton(pairedDevices: Set<BluetoothDevice>?)
        fun onClickedBackButton()
        fun onClickedApiSaveButton(apiAddress: String)
        fun onClickedPrinterMacAddressSaveButton(macAddress: String)
    }

    /**
     * Interactor interfésze
     */
    interface ISettingsInteractor{
        fun getApiAddress()
        fun getPrinter()
        fun getAppVersion()
        fun setApiAddress(apiAddress: String)
        fun setMacAddress(macAddress: String)
        fun searchPrinter(pairedDevices: Set<BluetoothDevice>?)
    }
}