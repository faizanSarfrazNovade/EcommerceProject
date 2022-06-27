package com.example.frd.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.frd.R
import com.example.frd.models.Cart
import com.example.frd.models.Order
import com.example.frd.ui.adapters.CartItemsListAdapter
import com.example.frd.utils.Constants
import kotlinx.android.synthetic.main.activity_my_order_details.*
import java.util.*

class MyOrderDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_order_details)
        setupActionBar()

        var myOrderDetails: Order = Order(cart = Cart())

        if (intent.hasExtra(Constants.EXTRA_MY_ORDER_DETAILS)) {
            myOrderDetails =
                intent.getParcelableExtra<Order>(Constants.EXTRA_MY_ORDER_DETAILS)!!
        }

        setupUI(myOrderDetails)
    }

    private fun setupActionBar() {

        setSupportActionBar(toolbar_my_order_details_activity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
        }

        toolbar_my_order_details_activity.setNavigationOnClickListener { onBackPressed() }
    }

    private fun setupUI(orderDetails: Order) {

        tv_order_details_id.text = orderDetails.id
        tv_order_details_date.text = orderDetails.orderDate.toString()

        rv_my_order_items_list.layoutManager = LinearLayoutManager(this@MyOrderDetailsActivity)
        rv_my_order_items_list.setHasFixedSize(true)

        val cartListAdapter =
            CartItemsListAdapter(this@MyOrderDetailsActivity, orderDetails.cart.product)
        rv_my_order_items_list.adapter = cartListAdapter

        tv_my_order_details_address.text =
            "${orderDetails.deliveryAddress.street}, ${orderDetails.deliveryAddress.zipCode}"
        tv_order_details_total_amount.text = orderDetails.total.toString()
    }
}