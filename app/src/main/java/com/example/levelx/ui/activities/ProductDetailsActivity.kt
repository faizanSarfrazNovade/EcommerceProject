package com.example.levelx.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.levelx.R
import com.example.levelx.models.Product
import com.example.levelx.ui.ui.fragments.CartListFragment
import com.example.levelx.utils.Constants
import kotlinx.android.synthetic.main.activity_product_details.*

class ProductDetailsActivity : BaseActivity(),View.OnClickListener {

    // A global variable for product id.
    private var mProductId: String = ""
    private lateinit var mProductDetails: Product

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)

        if (intent.hasExtra(Constants.EXTRA_PRODUCT_ID)) {
            mProductId =
                intent.getStringExtra(Constants.EXTRA_PRODUCT_ID)!!
            //Log.i("Product Id", mProductId)
        }
        setupActionBar()
        btn_go_to_cart.visibility = View.VISIBLE
        btn_add_to_cart.visibility = View.VISIBLE
        btn_add_to_cart.setOnClickListener(this)
        btn_go_to_cart.setOnClickListener(this)

        getProductDetails()
    }

    private fun setupActionBar() {

        setSupportActionBar(toolbar_product_details_activity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
        }

        toolbar_product_details_activity.setNavigationOnClickListener { onBackPressed() }
    }

    private fun getProductDetails() {

        // Show the product dialog
        showProgressDialog(resources.getString(R.string.please_wait))
        hideProgressDialog()
    }


    /**
     * A function to prepare the cart item to add it to the cart.
     */
    private fun addToCart() {
        showProgressDialog(resources.getString(R.string.please_wait))
        hideProgressDialog()
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {

                R.id.btn_add_to_cart -> {
                    addToCart()
                }

                R.id.btn_go_to_cart->{
                    startActivity(Intent(this@ProductDetailsActivity, CartListFragment::class.java))
                }
            }
        }
    }

}