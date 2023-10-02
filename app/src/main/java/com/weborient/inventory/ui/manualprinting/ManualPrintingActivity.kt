package com.weborient.inventory.ui.manualprinting

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.drawable.toBitmapOrNull
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.weborient.inventory.R
import com.weborient.inventory.databinding.ActivityManualPrintingBinding
import com.weborient.inventory.handlers.dialog.DialogHandler
import com.weborient.inventory.handlers.dialog.DialogResultEnums
import com.weborient.inventory.handlers.dialog.DialogTypeEnums
import com.weborient.inventory.handlers.dialog.IDialogResultHandler
import com.weborient.inventory.handlers.service.PhoneServiceHandler

class ManualPrintingActivity : AppCompatActivity(), IManualPrintingContract.IManualPrintingView, IDialogResultHandler {
    private val presenter = ManualPrintingPresenter(this)

    private var bluetoothAdapter: BluetoothAdapter? = null
    private var bluetoothManager: BluetoothManager? = null

    private lateinit var layoutText: TextInputLayout
    private lateinit var layoutQuantity: TextInputLayout
    private lateinit var layoutQRCodeEmpty: ConstraintLayout
    private lateinit var inputText: TextInputEditText
    private lateinit var inputQuantity: TextInputEditText
    private lateinit var imageView: ImageView

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityManualPrintingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        layoutText = binding.tilManualPrintingRawText
        layoutQuantity = binding.tilManualPrintingQuantity
        layoutQRCodeEmpty = binding.clQrcodeEmpty
        inputText = binding.etManualPrintingText
        inputQuantity = binding.etManualPrintingQuantity
        imageView = binding.ivManualPrintingQrCode

        binding.ivManualPrintingBack.setOnClickListener {
            presenter.onClickedBackButton()
        }

        binding.btManualPrintingGenerate.setOnClickListener {
            presenter.onClickedGenerateButton(inputText.text.toString())
        }

        binding.btManualPrintingPrint.setOnClickListener {
            if(PhoneServiceHandler.checkBluetoothState(this)){
                presenter.onClickedPrintButton(imageView.drawable?.toBitmapOrNull(), inputQuantity.text.toString().toIntOrNull(), bluetoothAdapter)
            }
            else{
                DialogHandler.showDialogWithResult(this, this, getString(R.string.dialog_settings_bluetooth_state), DialogTypeEnums.SettingsBluetooth)
            }
        }

        bluetoothManager = getSystemService(BluetoothManager::class.java)
        bluetoothAdapter = bluetoothManager?.adapter
    }

    override fun clearQRCode() {
        imageView.visibility = View.GONE
        layoutQRCodeEmpty.visibility = View.VISIBLE
        imageView.setImageDrawable(null)
    }

    override fun clearText() {
        inputText.text?.clear()
    }

    override fun clearAmount() {
        inputQuantity.text?.clear()
    }

    override fun showQRCode(bitmap: Bitmap) {
        imageView.setImageBitmap(bitmap)
        imageView.visibility = View.VISIBLE
        layoutQRCodeEmpty.visibility = View.GONE
    }

    override fun showTextError(error: String?) {
        layoutText.error = error
    }

    override fun showQuantityError(error: String?) {
        layoutQuantity.error = error
    }

    override fun showBluetoothDialog() {
        startActivity(Intent(Settings.ACTION_BLUETOOTH_SETTINGS))
    }

    override fun showInformationDialog(information: String, type: DialogTypeEnums) {
        DialogHandler.showInformationDialog(this, information, type)
    }

    override fun showProgress(information: String) {
        DialogHandler.showProgressDialog(this, information)
    }

    override fun hideProgress() {
        DialogHandler.closeProgressDialog()
    }

    override fun closeActivity() {
        finish()
    }

    override fun onDialogResult(result: DialogResultEnums) {
        presenter.onDialogResult(result)
    }
}