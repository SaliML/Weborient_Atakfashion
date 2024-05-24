package com.weborient.atakfashion.views.removal

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.weborient.atakfashion.R
import com.weborient.atakfashion.models.api.getdata.ProductData

class RemovalProductListAdapter(private val context: Context,  private var removaledProducts: ArrayList<ProductData>): RecyclerView.Adapter<RemovalProductListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.removaled_list_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = removaledProducts[position]

        holder.bind(product)
    }

    override fun getItemCount(): Int {
        return removaledProducts.size
    }

    fun setRemovaledProductList(products: ArrayList<ProductData>){
        this.removaledProducts = products
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        private var product: ProductData? = null

        private var textProductID: TextView? = null
        private var textProductName: TextView? = null
        private var textProductQuantity: TextView? = null
        private var imageProductPhoto: ImageView? = null

        init{
            textProductID = itemView.findViewById(R.id.tv_removaled_list_item_id)
            textProductName = itemView.findViewById(R.id.tv_removaled_list_item_name)
            textProductQuantity = itemView.findViewById(R.id.tv_removaled_list_item_quantity)
            imageProductPhoto = itemView.findViewById(R.id.iv_removaled_list_item_photo)
        }

        fun bind(productData: ProductData){
            product = productData

            textProductID?.text = product?.id
            textProductName?.text = product?.name
            textProductQuantity?.text = product?.quantity.toString()

            imageProductPhoto?.let {
                Glide.with(context).load(product?.pictureURL).placeholder(R.drawable.image_not_available).into(it)
            }
        }
    }
}