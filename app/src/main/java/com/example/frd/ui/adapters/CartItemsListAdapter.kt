package com.example.frd.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.frd.R
import com.example.frd.models.Product
import com.example.frd.ui.ui.fragments.CartListActivity
import com.example.frd.ui.viewmodels.CartListViewModel
import com.example.frd.utils.GlideLoader
import kotlinx.android.synthetic.main.item_cart_layout.view.*




open class CartItemsListAdapter (
    private val context: Context,
    private var list: ArrayList<Product>,
    var cartListViewModel: CartListViewModel = CartListViewModel() ,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_cart_layout,
                parent,
                false
            )
        )
    }

    @SuppressLint("StringFormatMatches")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var mLifecycleOwner:LifecycleOwner
        val product = list[position]

        if (holder is MyViewHolder) {

            GlideLoader(context).loadProductPicture(product.image1, holder.itemView.iv_cart_item_image)

            holder.itemView.tv_cart_item_title.text = product.name
            holder.itemView.tv_cart_item_price.text = "$${product.price}"
            mLifecycleOwner = context as LifecycleOwner
            cartListViewModel.currentNumber.observe(mLifecycleOwner, Observer {
                holder.itemView.tv_cart_quantity.text = it.toString()
            })

            if (product.stock.toString() == "0") {
                holder.itemView.ib_remove_cart_item.visibility = View.GONE
                holder.itemView.ib_add_cart_item.visibility = View.GONE
                holder.itemView.ib_delete_cart_item.visibility = View.VISIBLE
                holder.itemView.tv_cart_quantity.text =
                    context.resources.getString(R.string.lbl_out_of_stock)

                holder.itemView.tv_cart_quantity.setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.colorSnackBarError
                    )
                )
            } else {
                holder.itemView.ib_remove_cart_item.visibility = View.VISIBLE
                holder.itemView.ib_add_cart_item.visibility = View.VISIBLE
                holder.itemView.ib_delete_cart_item.visibility = View.VISIBLE
                holder.itemView.tv_cart_quantity.setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.colorSecondaryText
                    )
                )
            }
            holder.itemView.ib_delete_cart_item.setOnClickListener {
                when (context) {
                    is CartListActivity -> {
                        val preferences = context.getSharedPreferences("cart", Context.MODE_PRIVATE)
                        val prefsEditor = preferences.edit()
                        prefsEditor.putString("cart", "")
                        prefsEditor.commit()
                        // did not have time to implement a way to reload activity ? notifyDataSetChanged not refreshing activity
                    }
                }
            }

            holder.itemView.ib_remove_cart_item.setOnClickListener{
                cartListViewModel.currentNumber.value = --cartListViewModel.numberInCart
            }
            holder.itemView.ib_add_cart_item.setOnClickListener {
                cartListViewModel.currentNumber.value = ++cartListViewModel.numberInCart
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
    private class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
}