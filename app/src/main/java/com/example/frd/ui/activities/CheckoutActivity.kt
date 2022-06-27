package com.example.frd.ui.activities

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.frd.R
import com.example.frd.api.ApiClient
import com.example.frd.models.DeliveryAddress
import com.example.frd.models.Product
import com.example.frd.ui.adapters.CartItemsListAdapter
import com.example.frd.ui.viewmodels.CartListViewModel
import com.example.frd.ui.viewmodels.CheckoutViewModel
import kotlinx.android.synthetic.main.activity_checkout.*
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CheckoutActivity : BaseActivity() {

    private var mAddressDetails: DeliveryAddress? = null
    private var mSubTotal: Double = 0.0
    private var mTotalAmount: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)
        setupActionBar()
        if (mAddressDetails != null) {
            tv_checkout_address.text = "${mAddressDetails!!.street}, ${mAddressDetails!!.zipCode}"
        }
        btn_place_order.setOnClickListener {
            placeAnOrder()
        }
        val mPrefs = getSharedPreferences("userToken", Context.MODE_PRIVATE)
        var userToken = mPrefs.getString("userToken", "").toString()
        val checkoutViewModel = ViewModelProvider(this).get(CheckoutViewModel::class.java)
        checkoutViewModel.getCart(userToken).observe(this,{ cart ->
            successCartItemsList(cart.listProduct)
        })
    }

    private fun setupActionBar() {
        setSupportActionBar(toolbar_checkout_activity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
        }

        toolbar_checkout_activity.setNavigationOnClickListener { onBackPressed() }
    }


    private fun placeAnOrder() {
        val mPrefs = getSharedPreferences("userToken", Context.MODE_PRIVATE)
        var userToken = mPrefs.getString("userToken", "").toString()
        CoroutineScope(Dispatchers.IO).launch {
            ApiClient.apiService.submitOrder(userToken)
        }
    }

    fun successCartItemsList(products: ArrayList<Product>) {

        rv_cart_list_items.layoutManager = LinearLayoutManager(this@CheckoutActivity)
        rv_cart_list_items.setHasFixedSize(true)

        val cartListAdapter = CartItemsListAdapter(this@CheckoutActivity, products, CartListViewModel())
        rv_cart_list_items.adapter = cartListAdapter


        for (item in products) {
            mTotalAmount = item.price
        }
        tv_checkout_total_amount.text = "$$mTotalAmount"
        }

}