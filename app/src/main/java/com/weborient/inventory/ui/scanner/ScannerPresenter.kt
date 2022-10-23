package com.weborient.womo.ui.scanner

class ScannerPresenter(private val view: IScannerContract.IScannerView): IScannerContract.IScannerPresenter {
    private val interactor = ScannerInteractor(this)

    override fun scannerInitializationError() {
        view.closeActivityResultError()
    }

    override fun findItemByID(text: String) {
        interactor.findItemByID(text)
    }

    override fun onFoundItem() {
        view.closeActivityResultSuccessful()
    }

    override fun onNotFoundItem() {
        view.closeActivityResultError()
    }

}