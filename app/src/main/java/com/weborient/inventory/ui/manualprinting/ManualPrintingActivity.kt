package com.weborient.inventory.ui.manualprinting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.weborient.inventory.databinding.ActivityManualPrintingBinding

class ManualPrintingActivity : AppCompatActivity(), IManualPrintingContract.IManualPrintingView {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityManualPrintingBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}