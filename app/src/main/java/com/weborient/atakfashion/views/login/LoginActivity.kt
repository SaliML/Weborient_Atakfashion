package com.weborient.atakfashion.views.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.weborient.atakfashion.R
import com.weborient.atakfashion.databinding.ActivityLoginBinding
import com.weborient.atakfashion.handlers.dialog.DialogHandler
import com.weborient.atakfashion.handlers.dialog.DialogTypeEnums
import com.weborient.atakfashion.models.login.LoginResult
import com.weborient.atakfashion.repositories.settings.SettingsRepository
import com.weborient.atakfashion.ui.main.MainActivity
import com.weborient.atakfashion.viewmodels.login.LoginViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.btLoginLogin.setOnClickListener {
            binding.tilLoginUsername.error = null
            binding.tilLoginPassword.error = null

            val loginResult = viewModel.verifyUser()

            when(loginResult){
                LoginResult.Successful->{
                    navigateToMainActivity()
                }
                LoginResult.PasswordEmpty->{
                    binding.tilLoginPassword.error = "Kötelező kitölteni!"
                }
                LoginResult.PasswordNotEquals->{
                    DialogHandler.showInformationDialog(this, "Hibás jelszó!", DialogTypeEnums.Error)
                }
                LoginResult.UserNotExists->{
                    DialogHandler.showInformationDialog(this, "Felhasználó nem létezik!", DialogTypeEnums.Error)
                }
                LoginResult.UserEmpty->{
                    binding.tilLoginUsername.error = "Kötelező kitölteni!"
                }
            }
        }
    }

    /**
     * Navigálás a főoldalra
     */
    private fun navigateToMainActivity(){
        startActivity(Intent(this, MainActivity::class.java))
    }
}