package com.weborient.inventory.ui.settings

import android.bluetooth.BluetoothDevice
import com.weborient.inventory.handlers.dialog.DialogTypeEnums
import com.weborient.inventory.models.PrinterModel
import java.util.*

class SettingsPresenter(private val view: ISettingsContract.ISettingsView): ISettingsContract.ISettingsPresenter {
    private val interactor = SettingsInteractor(this)

    override fun getApiAddress() {
        interactor.getApiAddress()
    }

    override fun getMacAddress() {
        interactor.getMacAddress()
    }

    override fun getIPAddress() {
        interactor.getIPAddress()
    }

    override fun getCutSettings() {
        interactor.getCutSettings()
    }

    override fun getAppVersion() {
        interactor.getAppVersion()
    }

    override fun onFetchedApiAddress(apiAddress: String) {
        view.showApiAddress(apiAddress)
    }

    override fun onFetchedMacAddress(macAddress: String?) {
        view.showPrinterMacAddress(macAddress)
    }

    override fun onFetchedIPAddress(ipAddress: String?) {
        view.showPrinterIPAddress(ipAddress)
    }

    override fun onFetchedCutSettings(isAutoCut: Boolean, isCutAtEnd: Boolean) {
        view.showCutSettings(isAutoCut, isCutAtEnd)
    }

    override fun onFetchedPrinterName(printerName: String) {
        view.showPrinterName(printerName)
    }

    override fun onFetchedPrinterPairStatus(printerPairStatus: String) {
        view.showPrinterStatus(printerPairStatus)
    }

    override fun onFetchedAppVersion(version: String) {
        view.showAppVersion(version)
    }

    override fun refreshPrinter(pairedDevices: Set<BluetoothDevice>?) {
        interactor.searchPrinter(pairedDevices)
    }

    override fun onClickedBackButton() {
        view.closeActivity()
    }

    override fun onClickedApiSaveButton(apiAddress: String?) {
        if(!apiAddress.isNullOrEmpty()){
            view.showApiError(null)
            view.saveApiAddress(apiAddress)
            interactor.setApiAddress(apiAddress)

            view.showInformationDialog("Sikeres mentés", DialogTypeEnums.Successful)
        }
        else{
            view.showApiError("Kérem töltse ki a mezőt!")
        }
    }

    override fun onClickedPrinterSettingsSaveButton(ipAddress: String?, isAutoCut: Boolean, isCutAtEnd: Boolean) {
        if(!ipAddress.isNullOrEmpty()){
            view.showIPAddressError(null)
            view.saveIPAddress(ipAddress)
            view.saveCutSettings(isAutoCut, isCutAtEnd)
            interactor.setIPAddress(ipAddress)
            interactor.setCutSettings(isAutoCut, isCutAtEnd)

            view.showInformationDialog("Sikeres mentés", DialogTypeEnums.Successful)
        }
        else{
            view.showMacAddressError("Kérem töltse ki a mezőt!")
        }
    }
}