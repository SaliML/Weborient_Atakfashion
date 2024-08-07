package com.weborient.atakfashion.views.`in`

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.weborient.atakfashion.R
import com.weborient.atakfashion.databinding.ActivityInBinding
import com.weborient.atakfashion.handlers.dialog.DialogHandler
import com.weborient.atakfashion.handlers.dialog.DialogResultEnums
import com.weborient.atakfashion.handlers.dialog.DialogTypeEnums
import com.weborient.atakfashion.handlers.dialog.IDialogResultHandler
import com.weborient.atakfashion.handlers.service.PhoneServiceHandler
import com.weborient.atakfashion.models.api.getdata.ProductData
import com.weborient.atakfashion.ui.`in`.IInContract
import com.weborient.atakfashion.ui.`in`.IProductClickHandler
import com.weborient.atakfashion.ui.`in`.InPresenter
import com.weborient.atakfashion.ui.`in`.ProductListAdapter
import com.weborient.atakfashion.views.newproduct.NewProductFragment

class InActivity : AppCompatActivity(), IInContract.IInView, IProductClickHandler,
    IDialogResultHandler {
    private val presenter = InPresenter(this)

    private lateinit var recyclerItemList: RecyclerView

    private lateinit var layoutQuantity: TextInputLayout

    private lateinit var inputQuantity: TextInputEditText

    private lateinit var buttonPrint: ImageView

    private lateinit var checkboxPringGroup: CheckBox

    private var itemAdapter: ProductListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerItemList = binding.rvItemlist
        recyclerItemList.itemAnimator = null

        layoutQuantity = binding.tilInQuantity

        inputQuantity = binding.etInQuantity

        buttonPrint = binding.ivInPrint

        checkboxPringGroup = binding.cbInPrintGroup

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
            if(PhoneServiceHandler.checkWifiState(this)){
                if(checkboxPringGroup.isChecked){
                    //Csoportos nyomtatás esetén csak 1 db QR kódra van szükség
                    presenter.onClickedPrintButton("1")
                }
                else{
                    //Nem csoportos nyomtatás esetén tetszőleges mennyiségű QR kódra van szükség
                    presenter.onClickedPrintButton(inputQuantity.text.toString())
                }
            }
            else{
                DialogHandler.showDialogWithResult(this, this, getString(R.string.dialog_settings_wifi_state), DialogTypeEnums.SettingsWifi)
            }
        }

        presenter.getItems()
    }

    override fun onDestroy() {
        presenter.onClickedProduct(null)

        super.onDestroy()
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
        supportFragmentManager.beginTransaction().add(android.R.id.content, NewProductFragment()).commit()
    }

    override fun showItems(productList: ArrayList<ProductData>) {
        itemAdapter = ProductListAdapter(this, this, productList)
        recyclerItemList.adapter = itemAdapter
    }

    override fun refreshList() {
        itemAdapter?.notifyItemRangeChanged(0, itemAdapter!!.itemCount)
    }

    override fun closeActivity() {
        finish()
    }

    override fun onClickedProduct(product: ProductData?) {
        presenter.onClickedProduct(product)
    }
}