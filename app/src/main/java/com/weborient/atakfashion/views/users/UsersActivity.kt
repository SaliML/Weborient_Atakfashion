package com.weborient.atakfashion.views.users

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.weborient.atakfashion.R
import com.weborient.atakfashion.databinding.ActivityUsersBinding
import com.weborient.atakfashion.viewmodels.login.LoginViewModel

class UsersActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUsersBinding
    private val viewModel: LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_users)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }
}