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

    override fun getPrinter() {
        interactor.getPrinter()
    }

    override fun getAppVersion() {
        interactor.getAppVersion()
    }

    override fun onFetchedApiAddress(apiAddress: String) {
        view.showApiAddress(apiAddress)
    }

    override fun onFetchedMacAddress(macAddress: String) {
        view.showPrinterMacAddress(macAddress)
    }

    override fun onFetchedPrinter(printer: PrinterModel?) {
        printer?.let{ tempPrinter ->
            tempPrinter.name?.let{
                view.showPrinterName(it)
            }

            tempPrinter.macAddress?.let{
                view.showPrinterMacAddress(it)
            }

            tempPrinter.pairStatus?.let{
                if(it){
                    view.showPrinterStatus("Párosítva")
                }
                else{
                    view.showPrinterStatus("Nincs párosítva")
                }
            }?:run{
                view.showPrinterStatus("Nincs információ!")
            }
        }
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