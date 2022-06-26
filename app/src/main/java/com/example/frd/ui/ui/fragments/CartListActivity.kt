package com.example.frd.ui.ui.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.frd.R
import com.example.frd.api.ApiClient
import com.example.frd.models.Cart
import com.example.frd.models.Product
import com.example.frd.ui.activities.AddressListActivity
import com.example.frd.ui.activities.BaseActivity
import com.example.frd.ui.adapters.CartItemsListAdapter
import com.example.frd.ui.viewmodels.CartListViewModel
import com.example.frd.utils.Constants
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_cart_list.*
import kotlinx.android.synthetic.main.activity_product_details.*
import kotlinx.android.synthetic.main.item_cart_layout.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.internal.http.HTTP_OK
import java.util.*


class CartListActivity : BaseActivity() {
    var cartListViewModel: CartListViewModel = CartListViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart_list)

        setupActionBar()

        btn_checkout.setOnClickListener {
            val intent = Intent(this@CartListActivity, AddressListActivity::class.java)
            intent.putExtra(Constants.EXTRA_SELECT_ADDRESS, true)
            startActivity(intent)
            var products = getProductList()
            products[0].quantityOnCart = cartListViewModel.currentNumber.value!!
            val p = products[0]
            val mPrefs = getSharedPreferences("userToken", Context.MODE_PRIVATE)
            var userSession = mPrefs.getString("userToken", "").toString()
            val cart = Cart("", products, userSession, false, p.id, "", p.quantityOnCart, p.price.toLong())
            CoroutineScope(Dispatchers.IO).launch {
                Log.d("faizan", cart.toString())
                val res = ApiClient.apiService.submitCart(cart)

                if (res.code() == HTTP_OK){
                    startActivity(intent)
                }else {
                    showErrorSnackBar(resources.getString(R.string.err_msg_email_password_mismatch), true)
                }
            }
        }

    }

    override fun onResume() {
        super.onResume()
        getProductList()
    }

    private fun setupActionBar() {

        setSupportActionBar(toolbar_cart_list_activity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
        }

        toolbar_cart_list_activity.setNavigationOnClickListener { onBackPressed() }
    }


    private fun getProductList(): ArrayList<Product> {
        val mPrefs = getSharedPreferences("cart", Context.MODE_PRIVATE)
        val gson = Gson()
        val json: String? = mPrefs.getString("cart", "")
        val products = arrayListOf<Product>()
        if (json != null) {
            if (json.isNotEmpty()){
                products.add(gson.fromJson(json, Product::class.java))
                successProducts(products)
            }
        }
        return products
    }
    private fun successProducts(productsList: ArrayList<Product>) {

        if (productsList.size > 0) {

            rv_cart_items_list.visibility = View.VISIBLE
            ll_checkout.visibility = View.VISIBLE
            tv_no_cart_item_found.visibility = View.GONE

            rv_cart_items_list.layoutManager = LinearLayoutManager(this@CartListActivity)
            rv_cart_items_list.setHasFixedSize(true)

            val cartListAdapter = CartItemsListAdapter(this@CartListActivity, productsList)
            rv_cart_items_list.adapter = cartListAdapter

            var total = 0.0

            for (item in productsList) {

                val availableQuantity = item.stock

                if (availableQuantity > 0) {
                    total = item.price
                }
            }

            if (total > 0) {
                ll_checkout.visibility = View.VISIBLE
                tv_total_amount.text = "$$total"
            } else {
                ll_checkout.visibility = View.GONE
            }

        } else {
            rv_cart_items_list.visibility = View.GONE
            ll_checkout.visibility = View.GONE
            tv_no_cart_item_found.visibility = View.VISIBLE
        }

    }
}