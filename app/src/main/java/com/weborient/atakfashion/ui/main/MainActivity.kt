package com.weborient.atakfashion.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.weborient.atakfashion.databinding.ActivityMainBinding
import com.weborient.atakfashion.ui.edit.EditActivity
import com.weborient.atakfashion.ui.`in`.InActivity
import com.weborient.atakfashion.ui.manualprinting.ManualPrintingActivity
import com.weborient.atakfashion.ui.out.OutActivity
import com.weborient.atakfashion.ui.photos.PhotosActivity
import com.weborient.atakfashion.ui.settings.SettingsActivity
import com.weborient.atakfashion.views.RemovalActivity

class MainActivity : AppCompatActivity(), IMainContract.IMainView {
    private val presenter = MainPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cvMainIn.setOnClickListener {
            presenter.onClickedInButton()
        }

        binding.cvMainOut.setOnClickListener {
            presenter.onClickedOutButton()
        }

        binding.cvMainRemoval.setOnClickListener {
            presenter.onClickedRemovalButton()
        }

        binding.cvMainEdit.setOnClickListener{
            presenter.onClickedEditButton()
        }

        binding.cvMainManualPrint.setOnClickListener {
            presenter.onClickedManualPrintingButton()
        }

        binding.cvMainPhotos.setOnClickListener {
            presenter.onClickedPhotosButton()
        }

        binding.cvMainSettings.setOnClickListener {
            presenter.onClickedSettingsButton()
        }

        binding.cvMainExit.setOnClickListener {
            presenter.onClickedExitButton()
        }
    }

    /**
     * Navigálás a bevétel oldalra
     */
    override fun navigateToInActivity() {
        startActivity(Intent(this, InActivity::class.java))
    }

    /**
     * Navigálás a kiadás oldalra
     */
    override fun navigateToOutActivity() {
        startActivity(Intent(this, OutActivity::class.java))
    }

    /**
     * Navigálás a kiadott termékek oldalra
     */
    override fun navigateToRemovalActivity() {
        startActivity(Intent(this, RemovalActivity::class.java))
    }

    /**
     * Navigálás a szerkesztés oldalra
     */
    override fun navigateToEditActivity() {
        startActivity(Intent(this, EditActivity::class.java))
    }

    /**
     * Navigálás a beállítások oldalra
     */
    override fun navigateToSettingsActivity() {
        startActivity(Intent(this, SettingsActivity::class.java))
    }

    /**
     * Navigálás a manuális nyomtatás oldalra
     */
    override fun navigateToManualPrintingActivity() {
        startActivity(Intent(this, ManualPrintingActivity::class.java))
    }

    /**
     * Navigálás a fényképek oldalra
     */
    override fun navigateToPhotosActivity() {
        startActivity(Intent(this, PhotosActivity::class.java))
    }

    /**
     * Alkalmazás bezárása
     */
    override fun closeApplication() {
        finishAffinity()
        System.exit(0)
    }
}