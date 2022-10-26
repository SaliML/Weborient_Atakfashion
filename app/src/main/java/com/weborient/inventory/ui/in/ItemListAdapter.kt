package com.weborient.inventory.ui.`in`

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.weborient.inventory.R
import com.weborient.inventory.models.ItemModel

class ItemListAdapter(private val context: Context, private val handler: IItemClickHandler, private val itemList: ArrayList<ItemModel>): RecyclerView.Adapter<ItemListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_element_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val offerItem = itemList[position]

        holder.bind(offerItem)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        private var item: ItemModel? = null

        private var textItemName: TextView? = null
        private var textItemID: TextView? = null
        private var imageItemPhoto: ImageView? = null
        private var layoutItemCard: CardView? = null

        init{
            textItemName = itemView.findViewById(R.id.tv_list_item_name)
            textItemID = itemView.findViewById(R.id.tv_list_item_id)
            imageItemPhoto = itemView.findViewById(R.id.iv_list_item_photo)
            layoutItemCard = itemView.findViewById(R.id.cv_list_item)

            layoutItemCard?.setOnClickListener {
                handler.onClickedItem(item)
            }

        }

        fun bind(itemModel: ItemModel){
            item = itemModel

            textItemID?.text = item?.id
            textItemName?.text = item?.name

            imageItemPhoto?.let {
                val circularProgressDrawable = CircularProgressDrawable(context)
                circularProgressDrawable.strokeWidth = 5f
                circularProgressDrawable.centerRadius = 30f
                circularProgressDrawable.start()
                Glide.with(context).load(item?.photoURL).placeholder(circularProgressDrawable).into(it)
            }
        }
    }
}