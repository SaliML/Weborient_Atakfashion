package com.weborient.atakfashion.ui.settings

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import com.weborient.atakfashion.BuildConfig
import com.weborient.atakfashion.config.AppConfig
import com.weborient.atakfashion.handlers.printer.PrinterHandler
import com.weborient.atakfashion.models.QLPrinterLabelType
import java.util.*

class SettingsInteractor(private val presenter: ISettingsContract.ISettingsPresenter): ISettingsContract.ISettingsInteractor {
    override fun getSavedLabelSize() {
        presenter.onFetchedSavedLabelSize(AppConfig.printerLabelSize, AppConfig.labelSizeList)
    }

    override fun getApiAddress() {
        AppConfig.apiAddress?.let{
            presenter.onFetchedApiAddress(it)
        }
    }

    override fun getIPAddress() {
        presenter.onFetchedIPAddress(AppConfig.ipAddress)
    }

    override fun getCutSettings() {
        presenter.onFetchedCutSettings(AppConfig.isAutoCut, AppConfig.isCutAtEnd)
    }

    override fun getAppVersion() {
        presenter.onFetchedAppVersion(BuildConfig.VERSION_NAME)
    }

    override fun setApiAddress(apiAddress: String) {
        AppConfig.apiAddress = apiAddress
        AppConfig.apiServiceWithoutBearer = null
    }

    override fun setLabelSize(printerLabelType: QLPrinterLabelType){
        AppConfig.printerLabelSize = printerLabelType.id
    }

    override fun setIPAddress(ipAddress: String) {
        AppConfig.ipAddress = ipAddress
        presenter.onFetchedIPAddress(AppConfig.ipAddress)
    }

    override fun setCutSettings(isAutoCut: Boolean, isCutAtEnd: Boolean) {
        AppConfig.isAutoCut = isAutoCut
        AppConfig.isCutAtEnd = isCutAtEnd
    }
}