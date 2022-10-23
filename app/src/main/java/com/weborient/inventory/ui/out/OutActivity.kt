package com.weborient.inventory.ui.out

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import com.weborient.inventory.databinding.ActivityOutBinding
import com.weborient.womo.ui.scanner.ScannerActivity

class OutActivity : AppCompatActivity(), IOutContract.IOutView {
    private val presenter = OutPresenter(this)

    private lateinit var scannerActivityLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityOutBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun navigateToScannerActivity() {
        scannerActivityLauncher.launch(Intent(this, ScannerActivity::class.java))
    }
}