package com.weborient.inventory.ui.settings

import android.bluetooth.BluetoothDevice
import com.weborient.inventory.BuildConfig
import com.weborient.inventory.config.AppConfig
import com.weborient.inventory.repositories.printer.PrinterRepository
import java.util.*

class SettingsInteractor(private val presenter: ISettingsContract.ISettingsPresenter): ISettingsContract.ISettingsInteractor {
    override fun getApiAddress() {
        AppConfig.apiAddress?.let{
            presenter.onFetchedApiAddress(it)
        }
    }

    override fun getPrinter() {
        PrinterRepository.actualPrinter?.let{
            presenter.onFetchedPrinter(it)
        }
    }

    override fun getAppVersion() {
        presenter.onFetchedAppVersion(BuildConfig.VERSION_NAME)
    }

    override fun setApiAddress(apiAddress: String) {
        AppConfig.apiAddress = apiAddress
    }

    override fun setMacAddress(macAddress: String) {
        PrinterRepository.actualPrinter?.macAddress = macAddress.uppercase(Locale.getDefault())
    }

    override fun searchPrinter(pairedDevices: Set<BluetoothDevice>?) {
        PrinterRepository.setPrinter(pairedDevices)
        getPrinter()
    }
}