package com.weborient.womo.ui.scanner

/**
 * MVP minta a QR kód olvasó felületre
 */
interface IScannerContract {
    interface IScannerView{
        fun closeActivityResultSuccessful()
        fun closeActivityResultError()
    }

    interface IScannerPresenter{
        fun scannerInitializationError()
        fun findItemByID(text: String)
        fun onFoundItem()
        fun onNotFoundItem()
    }

    interface IScannerInteractor{
        fun findItemByID(text: String)
    }
}