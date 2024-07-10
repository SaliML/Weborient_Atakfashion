package com.weborient.atakfashion.ui.settings

import android.bluetooth.BluetoothDevice
import com.brother.sdk.lmprinter.setting.QLPrintSettings
import com.weborient.atakfashion.handlers.dialog.DialogTypeEnums
import com.weborient.atakfashion.models.QLPrinterLabelType
import kotlin.collections.ArrayList

class SettingsPresenter(private val view: ISettingsContract.ISettingsView): ISettingsContract.ISettingsPresenter {
    private val interactor = SettingsInteractor(this)

    override fun getApiAddress() {
        interactor.getApiAddress()
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

    override fun getSavedLabelSize() {
        interactor.getSavedLabelSize()
    }

    override fun onFetchedApiAddress(apiAddress: String) {
        view.showApiAddress(apiAddress)
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

    override fun onFetchedSavedLabelSize(savedLabelType: QLPrintSettings.LabelSize?, labelTypes: ArrayList<QLPrinterLabelType>) {
        view.selectLabelSize(savedLabelType, labelTypes)
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

    override fun onClickedPrinterSettingsSaveButton(ipAddress: String?, isAutoCut: Boolean, isCutAtEnd: Boolean, printerLabelType: QLPrinterLabelType?) {
        if(!ipAddress.isNullOrEmpty()){
            view.showIPAddressError(null)
            view.saveIPAddress(ipAddress)
            view.saveCutSettings(isAutoCut, isCutAtEnd)

            if(printerLabelType != null){
                view.savePrinterLabel(printerLabelType.id.ordinal)
                interactor.setLabelSize(printerLabelType)
            }

            interactor.setIPAddress(ipAddress)
            interactor.setCutSettings(isAutoCut, isCutAtEnd)

            view.showInformationDialog("Sikeres mentés", DialogTypeEnums.Successful)
        }
        else{
            view.showIPAddressError("Kérem töltse ki a mezőt!")
        }
    }
}