package com.weborient.inventory.ui.`in`

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.weborient.inventory.databinding.ActivityInBinding

class InActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityInBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}