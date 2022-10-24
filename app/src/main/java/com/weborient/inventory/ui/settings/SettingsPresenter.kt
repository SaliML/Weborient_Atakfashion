package com.weborient.inventory.ui.settings

import android.bluetooth.BluetoothDevice
import com.weborient.inventory.models.PrinterModel

class SettingsPresenter(private val view: ISettingsContract.ISettingsView): ISettingsContract.ISettingsPresenter {
    private val interactor = SettingsInteractor(this)

    override fun getApiAddress() {
        interactor.getApiAddress()
    }

    override fun getMacAddress() {
        interactor.getMacAddress()
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

    override fun onClickedApiSaveButton(apiAddress: String) {
        if(apiAddress.isNotEmpty()){
            view.saveApiAddress(apiAddress)
            interactor.setApiAddress(apiAddress)
        }
    }

    override fun onClickedPrinterMacAddressSaveButton(macAddress: String) {
        if(macAddress.isNotEmpty()){
            view.saveMacAddress(macAddress)
            interactor.setMacAddress(macAddress)
        }
    }
}