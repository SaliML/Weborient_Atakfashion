package com.weborient.atakfashion.ui.main

class MainPresenter(private val view: IMainContract.IMainView): IMainContract.IMainPresenter {
    /**
     * Bevétel gomb eseménye
     */
    override fun onClickedInButton() {
        view.navigateToInActivity()
    }

    /**
     * Kiadás gomb eseménye
     */
    override fun onClickedOutButton() {
        view.navigateToOutActivity()
    }

    /**
     * Kiadott termékek gomb eseménye
     */
    override fun onClickedRemovalButton() {
        view.navigateToRemovalActivity()
    }

    /**
     * Szerkesztés gomb eseménye
     */
    override fun onClickedEditButton() {
        view.navigateToEditActivity()
    }

    /**
     * Manuális nyomtatás gomb eseménye
     */
    override fun onClickedManualPrintingButton() {
        view.navigateToManualPrintingActivity()
    }

    /**
     * Navigálás a fényképek oldalra
     */
    override fun onClickedPhotosButton() {
        view.navigateToPhotosActivity()
    }

    /**
     * Beállítások gomb eseménye
     */
    override fun onClickedSettingsButton() {
        view.navigateToSettingsActivity()
    }

    /**
     * Kilépés gomb eseménye
     */
    override fun onClickedExitButton() {
        view.closeApplication()
    }
}