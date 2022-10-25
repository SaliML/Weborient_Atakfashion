package com.weborient.inventory.ui.main
/**
 * MVP minta a főoldalra
 */
interface IMainContract {
    /**
     * View interfésze
     */
    interface IMainView{
        /**
         * Navigálás a bevétel oldalra
         */
        fun navigateToInActivity()

        /**
         * Navigálás a kiadás oldalra
         */
        fun navigateToOutActivity()

        /**
         * Navigálás a beállítások oldalra
         */
        fun navigateToSettingsActivity()

        /**
         * Navigálás a manuális nyomtatás oldalra
         */
        fun navigateToManualPrintingActivity()

        /**
         * Alkalmazás bezárása
         */
        fun closeApplication()
    }

    /**
     * Presenter interfésze
     */
    interface IMainPresenter{
        /**
         * Bevétel gomb eseménye
         */
        fun onClickedInButton()

        /**
         * Kiadás gomb eseménye
         */
        fun onClickedOutButton()

        /**
         * Manuális nyomtatás eseménye
         */
        fun onClickedManualPrintingButton()

        /**
         * Beállítások gomb eseménye
         */
        fun onClickedSettingsButton()

        /**
         * Kilépés gomb eseménye
         */
        fun onClickedExitButton()
    }

    /**
     * Interactor interfésze
     */
    interface IMainInteractor{

    }
}