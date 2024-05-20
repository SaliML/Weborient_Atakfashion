package com.weborient.atakfashion.views

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.weborient.atakfashion.R
import com.weborient.atakfashion.databinding.ActivityRemovalBinding
import com.weborient.atakfashion.viewmodels.RemovalViewModel

class RemovalActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRemovalBinding
    private val viewModel: RemovalViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_removal)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }
}