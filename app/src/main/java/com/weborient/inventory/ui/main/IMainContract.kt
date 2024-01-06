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
         * Navigálás a termék szerkesztése oldalra
         */
        fun navigateToEditActivity()

        /**
         * Navigálás a beállítások oldalra
         */
        fun navigateToSettingsActivity()

        /**
         * Navigálás a manuális nyomtatás oldalra
         */
        fun navigateToManualPrintingActivity()

        /**
         * Navigálás a fényképek oldalra
         */
        fun navigateToPhotosActivity()

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
         * Szerkesztés gomb eseménye
         */
        fun onClickedEditButton()

        /**
         * Manuális nyomtatás gomb eseménye
         */
        fun onClickedManualPrintingButton()

        /**
         * Fényképek gomb eseménye
         */
        fun onClickedPhotosButton()

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