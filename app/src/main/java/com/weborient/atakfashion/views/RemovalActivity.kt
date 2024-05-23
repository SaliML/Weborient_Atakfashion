package com.weborient.atakfashion.views

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.DatePicker
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.weborient.atakfashion.R
import com.weborient.atakfashion.config.AppConfig
import com.weborient.atakfashion.databinding.ActivityRemovalBinding
import com.weborient.atakfashion.viewmodels.RemovalViewModel
import java.text.SimpleDateFormat
import java.util.Calendar

class RemovalActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRemovalBinding
    private val viewModel: RemovalViewModel by viewModels()

    private lateinit var removaledProductAdapter: RemovalProductListAdapter

    private lateinit var recyclerViewRemovaledProducts: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_removal)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        removaledProductAdapter = RemovalProductListAdapter(this, arrayListOf())

        recyclerViewRemovaledProducts = binding.rvRemovalProducts
        recyclerViewRemovaledProducts.adapter = removaledProductAdapter

        binding.btRemovalDatepicker.setOnClickListener {
            showDatePickerDialog(Calendar.getInstance())
        }

        binding.ivRemovalPdf.setOnClickListener {
            viewModel.exportRemovaledProductsToPDF(this)
        }

        viewModel.removaledProductList.observe(this) {
            removaledProductAdapter.setRemovaledProductList(it)
        }

        viewModel.selectedDate.observe(this) {
            binding.btRemovalDatepicker.text = SimpleDateFormat(AppConfig.DATE_FORMAT2).format(it)
            viewModel.getRemovaledProducts(this)
        }
    }

    /**
     * Dátumválasztó ablak megnyitása
     */
    private fun showDatePickerDialog(calendar: Calendar){
        val dtDialog = android.app.AlertDialog.Builder(this, R.style.DatePicker)
        val dtLayout = LayoutInflater.from(this).inflate(R.layout.date_picker_layout, null)

        dtDialog.setView(dtLayout)

        val timePickerButton = dtLayout.findViewById<Button>(R.id.bt_datepicker_save)
        val datePicker = dtLayout.findViewById<DatePicker>(R.id.datePicker)
        val currentDialog = dtDialog.create()

        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(
            Calendar.DAY_OF_MONTH), null)

        timePickerButton.setOnClickListener { view ->
            calendar.set(datePicker.year, datePicker.month, datePicker.dayOfMonth)

            viewModel.selectedDate.value = calendar.time

            currentDialog.dismiss()
        }

        currentDialog.show()
    }
}