package com.example.frd.ui.activities

import android.os.Bundle
import com.example.frd.R
import com.example.frd.models.Cart
import com.example.frd.models.DeliveryAddress
import com.example.frd.models.Product
import kotlinx.android.synthetic.main.activity_checkout.*

class CheckoutActivity : BaseActivity() {

    private var mAddressDetails: DeliveryAddress? = null
    private lateinit var mProductsList: ArrayList<Product>
    private lateinit var mCartItemsList: ArrayList<Cart>
    private var mSubTotal: Double = 0.0
    private var mTotalAmount: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)
        setupActionBar()
        
        if (mAddressDetails != null) {
            tv_checkout_full_name.text = mAddressDetails?.nameCustomer
            tv_checkout_address.text = "${mAddressDetails!!.street}, ${mAddressDetails!!.postalCode}"
            tv_checkout_mobile_number.text = mAddressDetails?.number.toString()
        }
        btn_place_order.setOnClickListener {
            placeAnOrder()
        }
        getProductList()
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
    /**
     * A function to get product list to compare the current stock with the cart items.
     */
    private fun getProductList() {

        // Show the progress dialog.
        showProgressDialog(resources.getString(R.string.please_wait))
        hideProgressDialog()
    }

    /**
     * A function to prepare the Order details to place an order.
     */
    private fun placeAnOrder() {

        // Show the progress dialog.
        showProgressDialog(resources.getString(R.string.please_wait))
        hideProgressDialog()
    }

}