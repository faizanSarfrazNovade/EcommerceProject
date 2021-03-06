package com.example.frd.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.frd.R
import com.example.frd.models.Product
import com.example.frd.ui.ui.fragments.CartListActivity
import com.example.frd.ui.viewmodels.ProductViewModel
import com.example.frd.utils.Constants
import com.example.frd.utils.GlideLoader
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_product_details.*




class ProductDetailsActivity : BaseActivity(),View.OnClickListener {

    // A global variable for product id.
    private var mProductId: String = ""
    private lateinit var mProductDetails: Product
    private lateinit var productViewModel: ProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)

        if (intent.hasExtra(Constants.EXTRA_PRODUCT_ID)) {
            mProductId = intent.getStringExtra(Constants.EXTRA_PRODUCT_ID)!!
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

    fun getProductDetails() {
        productViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)

        productViewModel.getProduct(mProductId).observe(this, { product ->
            productDetailsSuccess(product)
        })

    }


    fun productDetailsSuccess(product: Product) {

        mProductDetails = product

        // Populate the product details in the UI.
        if (product.image1 != null){
            GlideLoader(this@ProductDetailsActivity).loadProductPicture(
                product.image1,
                iv_product_detail_image
            )
        }


        tv_product_details_title.text = product.name
        tv_product_details_price.text = if (product.price != null) "$${product.price}" else "0"
        tv_product_details_description.text = product.description
        tv_product_details_stock_quantity.text = if (product.stock != null) product.stock.toString() else "0"

        if(product.stock == 0){

            // Hide the AddToCart button if the item is already in the cart.
            btn_add_to_cart.visibility = View.GONE

            tv_product_details_stock_quantity.text =
                resources.getString(R.string.lbl_out_of_stock)

            tv_product_details_stock_quantity.setTextColor(
                ContextCompat.getColor(
                    this@ProductDetailsActivity,
                    R.color.colorSnackBarError
                )
            )
        }
    }

    /**
     * A function to prepare the cart item to add it to the cart.
     */
    private fun addToCart() {
        val mPrefs = getSharedPreferences("cart", Context.MODE_PRIVATE)
        val prefsEditor = mPrefs.edit()
        val gson = Gson()
        val json = gson.toJson(mProductDetails)
        prefsEditor.putString("cart", json)
        prefsEditor.commit()
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.btn_add_to_cart -> {
                    addToCart()
                }
                R.id.btn_go_to_cart->{
                    startActivity(Intent(this@ProductDetailsActivity, CartListActivity::class.java))
                }
            }
        }
    }

}