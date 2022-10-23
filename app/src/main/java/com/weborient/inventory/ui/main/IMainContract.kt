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