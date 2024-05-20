package com.weborient.atakfashion.ui.scanner

/**
 * MVP minta a QR kód olvasó felületre
 */
interface IScannerContract {
    interface IScannerView{
        fun closeActivityWithResult(result: String?)
    }

    interface IScannerPresenter{
        fun scannerInitializationError()
        fun onScannedResult(result: String)
    }
}