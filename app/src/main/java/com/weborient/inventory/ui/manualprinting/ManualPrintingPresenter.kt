package com.weborient.inventory.ui.manualprinting

class ManualPrintingPresenter(private val view: IManualPrintingContract.IManualPrintingView): IManualPrintingContract.IManualPrintingPresenter {
    private val interactor = ManualPrintingInteractor(this)
}