package com.weborient.inventory.ui.scanner

class ScannerPresenter(private val view: IScannerContract.IScannerView): IScannerContract.IScannerPresenter {
    override fun scannerInitializationError() {
        view.closeActivityWithResult(null)
    }

    override fun onScannedResult(result: String) {
        view.closeActivityWithResult(result)
    }
}