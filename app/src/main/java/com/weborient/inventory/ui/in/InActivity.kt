package com.weborient.inventory.ui.`in`

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.weborient.inventory.R
import com.weborient.inventory.databinding.ActivityInBinding
import com.weborient.inventory.handlers.dialog.DialogHandler
import com.weborient.inventory.handlers.dialog.DialogResultEnums
import com.weborient.inventory.handlers.dialog.DialogTypeEnums
import com.weborient.inventory.handlers.dialog.IDialogResultHandler
import com.weborient.inventory.handlers.service.PhoneServiceHandler
import com.weborient.inventory.models.ItemModel
import com.weborient.inventory.ui.newitem.NewItemFragment

class InActivity : AppCompatActivity(), IInContract.IInView, IItemClickHandler,
    IDialogResultHandler {
    private val presenter = InPresenter(this)

    private lateinit var recyclerItemList: RecyclerView

    private lateinit var layoutQuantity: TextInputLayout

    private lateinit var inputQuantity: TextInputEditText

    private lateinit var buttonPrint: ImageView

    private var bluetoothAdapter: BluetoothAdapter? = null
    private var bluetoothManager: BluetoothManager? = null

    private var itemAdapter: ItemListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerItemList = binding.rvItemlist
        recyclerItemList.itemAnimator = null

        layoutQuantity = binding.tilInQuantity

        inputQuantity = binding.etInQuantity

        buttonPrint = binding.ivInPrint

        binding.swlRefresh.setOnRefreshListener {
            presenter.getItems()
            binding.swlRefresh.isRefreshing = false
        }

        binding.ivInBack.setOnClickListener {
            presenter.onClickedBackButton()
        }

        binding.ivInAdd.setOnClickListener {
            presenter.onClickedAddButton()
        }

        binding.ivInUpload.setOnClickListener {
            if(PhoneServiceHandler.checkNetworkState(this)){
                presenter.onClickedUploadButton(inputQuantity.text.toString())
            }
            else{
                DialogHandler.showDialogWithResult(this, this, getString(R.string.dialog_settings_network_state), DialogTypeEnums.SettingsNetwork)
            }
        }

        binding.ivInPrint.setOnClickListener {
            if(PhoneServiceHandler.checkBluetoothState(this)){
                presenter.onClickedPrintButton(inputQuantity.text.toString(), bluetoothAdapter)
            }
            else{
                DialogHandler.showDialogWithResult(this, this, getString(R.string.dialog_settings_bluetooth_state), DialogTypeEnums.SettingsBluetooth)
            }

        }

        bluetoothManager = getSystemService(BluetoothManager::class.java)
        bluetoothAdapter = bluetoothManager?.adapter

        presenter.getItems()
    }

    override fun showPrintButton() {
        buttonPrint.visibility = View.VISIBLE
    }

    override fun showProgress(information: String) {
        DialogHandler.showProgressDialog(this, information)
    }

    override fun showInformationDialog(information: String, type: DialogTypeEnums) {
        DialogHandler.showInformationDialog(this, information, type)
    }

    override fun showQuantityError(error: String?) {
        layoutQuantity.error = error
    }

    override fun hideProgress() {
        DialogHandler.closeProgressDialog()
    }

    override fun hidePrintButton() {
        buttonPrint.visibility = View.GONE
    }

    override fun clearQuantity() {
        inputQuantity.text?.clear()
    }

    override fun onDialogResult(result: DialogResultEnums) {
        presenter.onDialogResult(result)
    }

    override fun showBluetoothDialog() {
        startActivity(Intent(Settings.ACTION_BLUETOOTH_SETTINGS))
    }
    override fun showNetworkDialog() {
        startActivity(Intent(Settings.ACTION_WIRELESS_SETTINGS))
    }

    override fun showAddNewItemFragment() {
        supportFragmentManager.beginTransaction().add(android.R.id.content, NewItemFragment()).commit()
    }

    override fun showItems(itemList: ArrayList<ItemModel>) {
        itemAdapter = ItemListAdapter(this, this, itemList)
        recyclerItemList.adapter = itemAdapter
    }

    override fun refreshList() {
        itemAdapter?.notifyItemRangeChanged(0, itemAdapter!!.itemCount)
    }

    override fun closeActivity() {
        finish()
    }

    override fun onClickedItem(item: ItemModel?) {
        presenter.onClickedItem(item)
    }
}