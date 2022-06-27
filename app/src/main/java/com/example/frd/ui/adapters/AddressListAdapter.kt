package com.example.frd.ui.adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.frd.R
import com.example.frd.models.DeliveryAddress
import com.example.frd.ui.activities.AddEditAddressActivity
import com.example.frd.ui.activities.CheckoutActivity
import com.example.frd.utils.Constants
import kotlinx.android.synthetic.main.item_address_layout.view.*

/**
 * An adapter class for AddressList adapter.
 */
open class AddressListAdapter(
    private val context: Context,
    private var list: MutableList<DeliveryAddress>,
    private val selectAddress: Boolean
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_address_layout,
                parent,
                false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val address = list[position]

        if (holder is MyViewHolder) {
            holder.itemView.tv_address_full_name.text = address.street
            holder.itemView.tv_address_details.text = "${address.city}, ${address.zipCode}"
            holder.itemView.setOnClickListener {
                val intent = Intent(context, CheckoutActivity::class.java)
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }


    fun notifyEditItem(activity: Activity, position: Int) {
        val intent = Intent(context, AddEditAddressActivity::class.java)
        intent.putExtra(Constants.EXTRA_ADDRESS_DETAILS, list[position])

        activity.startActivityForResult(intent, Constants.ADD_ADDRESS_REQUEST_CODE)

        notifyItemChanged(position)
    }

    private class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
}