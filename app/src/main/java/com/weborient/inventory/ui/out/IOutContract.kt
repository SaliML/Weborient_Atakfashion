package com.weborient.inventory.ui.out

/**
 * MVP minta a kiadás felületre
 */
interface IOutContract {
    /**
     * View interfésze
     */
    interface IOutView{
        /**
         * Navigálás a QR kód olvasó felületre
         */
        fun navigateToScannerActivity()
    }

    /**
     * Presenter interfésze
     */
    interface IOutPresenter{

    }

    /**
     * Interactor interfésze
     */
    interface IOutInteractor{

    }
}