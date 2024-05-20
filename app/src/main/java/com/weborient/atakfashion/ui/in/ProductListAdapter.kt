package com.weborient.atakfashion.ui.`in`

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.weborient.atakfashion.R
import com.weborient.atakfashion.models.api.getdata.ProductData

class ProductListAdapter(private val context: Context, private val handler: IProductClickHandler, private val productList: ArrayList<ProductData>): RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_element_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val offerItem = productList[position]

        holder.bind(offerItem)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        private var product: ProductData? = null

        private var layout: LinearLayout? = null
        private var textItemName: TextView? = null
        private var textItemID: TextView? = null
        private var imageItemPhoto: ImageView? = null
        private var layoutItemCard: CardView? = null

        init{
            layout = itemView.findViewById(R.id.ll_list_item_layout)
            textItemName = itemView.findViewById(R.id.tv_list_item_name)
            textItemID = itemView.findViewById(R.id.tv_list_item_id)
            imageItemPhoto = itemView.findViewById(R.id.iv_list_item_photo)
            layoutItemCard = itemView.findViewById(R.id.cv_list_item)

            layoutItemCard?.setOnClickListener {

                handler.onClickedProduct(product)
            }

        }

        fun bind(productData: ProductData){
            product = productData

            textItemID?.text = product?.id
            textItemName?.text = product?.name

            if(product?.isSelected == true){
                layout?.setBackgroundResource(R.drawable.selected_item_background)
            }
            else{
                layout?.setBackgroundResource(R.drawable.unselected_item_background)
            }

            imageItemPhoto?.let {
                Glide.with(context).load(product?.pictureURL).placeholder(R.drawable.image_not_available).into(it)
            }
        }
    }
}