package com.weborient.atakfashion.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.transition.Visibility
import com.weborient.atakfashion.databinding.ActivityMainBinding
import com.weborient.atakfashion.models.user.UserPermission
import com.weborient.atakfashion.repositories.settings.SettingsRepository
import com.weborient.atakfashion.ui.edit.EditActivity
import com.weborient.atakfashion.ui.`in`.InActivity
import com.weborient.atakfashion.ui.manualprinting.ManualPrintingActivity
import com.weborient.atakfashion.ui.out.OutActivity
import com.weborient.atakfashion.ui.photos.PhotosActivity
import com.weborient.atakfashion.ui.settings.SettingsActivity
import com.weborient.atakfashion.views.removal.RemovalActivity
import com.weborient.atakfashion.views.users.UsersActivity

class MainActivity : AppCompatActivity(), IMainContract.IMainView {
    private val presenter = MainPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (SettingsRepository.loggedUser?.permissions?.contains(UserPermission.In) == true){
            binding.cvMainIn.visibility = View.VISIBLE
        }

        if (SettingsRepository.loggedUser?.permissions?.contains(UserPermission.Out) == true){
            binding.cvMainOut.visibility = View.VISIBLE
        }

        if (SettingsRepository.loggedUser?.permissions?.contains(UserPermission.Removal) == true){
            binding.cvMainRemoval.visibility = View.VISIBLE
        }

        if (SettingsRepository.loggedUser?.permissions?.contains(UserPermission.Edit) == true){
            binding.cvMainEdit.visibility = View.VISIBLE
        }

        if (SettingsRepository.loggedUser?.permissions?.contains(UserPermission.ManualPrinting) == true){
            binding.cvMainManualPrint.visibility = View.VISIBLE
        }

        if (SettingsRepository.loggedUser?.permissions?.contains(UserPermission.Photos) == true){
            binding.cvMainPhotos.visibility = View.VISIBLE
        }

        if (SettingsRepository.loggedUser?.permissions?.contains(UserPermission.Users) == true){
            binding.cvMainUsers.visibility = View.VISIBLE
        }

        if (SettingsRepository.loggedUser?.permissions?.contains(UserPermission.Settings) == true){
            binding.cvMainSettings.visibility = View.VISIBLE
        }

        binding.cvMainIn.setOnClickListener {
            //presenter.onClickedInButton()
            startActivity(Intent(this, InActivity::class.java))
        }

        binding.cvMainOut.setOnClickListener {
            //presenter.onClickedOutButton()
            startActivity(Intent(this, OutActivity::class.java))
        }

        binding.cvMainRemoval.setOnClickListener {
            //presenter.onClickedRemovalButton()
            startActivity(Intent(this, RemovalActivity::class.java))
        }

        binding.cvMainEdit.setOnClickListener{
            //presenter.onClickedEditButton()
            startActivity(Intent(this, EditActivity::class.java))
        }

        binding.cvMainManualPrint.setOnClickListener {
            //presenter.onClickedManualPrintingButton()
            startActivity(Intent(this, ManualPrintingActivity::class.java))
        }

        binding.cvMainPhotos.setOnClickListener {
            //presenter.onClickedPhotosButton()
            startActivity(Intent(this, PhotosActivity::class.java))
        }

        binding.cvMainUsers.setOnClickListener {
            startActivity(Intent(this, UsersActivity::class.java))
        }

        binding.cvMainSettings.setOnClickListener {
            //presenter.onClickedSettingsButton()
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        binding.cvMainExit.setOnClickListener {
            //presenter.onClickedExitButton()
            finishAffinity()
            System.exit(0)
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