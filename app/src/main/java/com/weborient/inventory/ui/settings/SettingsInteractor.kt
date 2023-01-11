package com.weborient.inventory.ui.settings

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import com.weborient.inventory.BuildConfig
import com.weborient.inventory.config.AppConfig
import com.weborient.inventory.handlers.printer.PrinterHandler
import java.util.*

class SettingsInteractor(private val presenter: ISettingsContract.ISettingsPresenter): ISettingsContract.ISettingsInteractor {
    override fun getApiAddress() {
        AppConfig.apiAddress?.let{
            presenter.onFetchedApiAddress(it)
        }
    }

    override fun getMacAddress() {
        presenter.onFetchedMacAddress(AppConfig.macAddress)
    }

    override fun getAppVersion() {
        presenter.onFetchedAppVersion(BuildConfig.VERSION_NAME)
    }

    override fun setApiAddress(apiAddress: String) {
        AppConfig.apiAddress = apiAddress
        AppConfig.apiServiceWithoutBearer = null
    }

    override fun setMacAddress(macAddress: String) {
        AppConfig.macAddress = macAddress.uppercase(Locale.getDefault())
        presenter.onFetchedMacAddress(AppConfig.macAddress)
    }

    @SuppressLint("MissingPermission")
    override fun searchPrinter(pairedDevices: Set<BluetoothDevice>?) {
       val printer = PrinterHandler.searchPrinter(pairedDevices, AppConfig.macAddress)

        if(printer != null){
            presenter.onFetchedPrinterName(printer.name)
            presenter.onFetchedMacAddress(AppConfig.macAddress)
            presenter.onFetchedPrinterPairStatus("Párosítva")
        }
        else{
            presenter.onFetchedPrinterName("-")
            presenter.onFetchedMacAddress(AppConfig.macAddress)
            presenter.onFetchedPrinterPairStatus("-")

        }
    }
}