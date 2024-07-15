package com.weborient.atakfashion.views.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.weborient.atakfashion.R
import com.weborient.atakfashion.databinding.ActivityMainBinding
import com.weborient.atakfashion.models.user.UserPermission
import com.weborient.atakfashion.repositories.settings.SettingsRepository
import com.weborient.atakfashion.ui.edit.EditActivity
import com.weborient.atakfashion.views.`in`.InActivity
import com.weborient.atakfashion.ui.manualprinting.ManualPrintingActivity
import com.weborient.atakfashion.ui.out.OutActivity
import com.weborient.atakfashion.ui.photos.PhotosActivity
import com.weborient.atakfashion.ui.settings.SettingsActivity
import com.weborient.atakfashion.viewmodels.main.MainViewModel
import com.weborient.atakfashion.views.removal.RemovalActivity
import com.weborient.atakfashion.views.users.UsersActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.cvMainIn.setOnClickListener {
            startActivity(Intent(this, InActivity::class.java))
        }

        binding.cvMainOut.setOnClickListener {
            startActivity(Intent(this, OutActivity::class.java))
        }

        binding.cvMainRemoval.setOnClickListener {
            startActivity(Intent(this, RemovalActivity::class.java))
        }

        binding.cvMainEdit.setOnClickListener{
            startActivity(Intent(this, EditActivity::class.java))
        }

        binding.cvMainManualPrint.setOnClickListener {
            startActivity(Intent(this, ManualPrintingActivity::class.java))
        }

        binding.cvMainPhotos.setOnClickListener {
            startActivity(Intent(this, PhotosActivity::class.java))
        }

        binding.cvMainUsers.setOnClickListener {
            startActivity(Intent(this, UsersActivity::class.java))
        }

        binding.cvMainSettings.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        binding.cvMainExit.setOnClickListener {
            finishAffinity()
            System.exit(0)
        }
    }

    override fun onResume() {
        super.onResume()

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
    }
}