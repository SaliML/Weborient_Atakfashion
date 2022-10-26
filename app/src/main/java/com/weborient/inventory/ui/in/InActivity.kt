package com.weborient.inventory.ui.`in`

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.weborient.inventory.databinding.ActivityInBinding
import com.weborient.inventory.models.ItemModel

class InActivity : AppCompatActivity(), IInContract.IInView, IItemClickHandler {
    private val presenter = InPresenter(this)

    private lateinit var recyclerItemList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerItemList = binding.rvItemlist

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

        presenter.getItems()
    }

    override fun showAddNewItemFragment() {

    }

    override fun showItems(itemList: ArrayList<ItemModel>) {
        val listAdapter = ItemListAdapter(this, this, itemList)
        recyclerItemList.adapter = listAdapter
    }

    override fun closeActivity() {
        finish()
    }

    override fun onClickedItem(item: ItemModel?) {
        //Meglévő termék Fragment megnyitása
    }
}