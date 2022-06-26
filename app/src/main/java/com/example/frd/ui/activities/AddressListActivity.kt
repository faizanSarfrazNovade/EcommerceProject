package com.example.frd.ui.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.frd.R
import com.example.frd.models.DeliveryAddress
import com.example.frd.ui.adapters.AddressListAdapter
import com.example.frd.ui.viewmodels.AddAddressViewModel
import com.example.frd.utils.Constants
import com.myshoppal.utils.SwipeToEditCallback
import kotlinx.android.synthetic.main.activity_add_edit_address.*
import kotlinx.android.synthetic.main.activity_address_list.*
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.activity_settings.*

class AddressListActivity : BaseActivity() {
    private var mSelectAddress: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address_list)
        val addressViewModel = ViewModelProvider(this).get(AddAddressViewModel::class.java)

        setupActionBar()

        if (mSelectAddress) {
            tv_title_add_address.text = resources.getString(R.string.title_select_address)
        }

        tv_add_address.setOnClickListener {
            val intent = Intent(this@AddressListActivity, AddEditAddressActivity::class.java)
            startActivityForResult(intent, Constants.ADD_ADDRESS_REQUEST_CODE)
        }
        getAddressList()
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Constants.ADD_ADDRESS_REQUEST_CODE) {

                getAddressList()
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            // A log is printed when user close or cancel the image selection.
            Log.e("Request Cancelled", "To add the address.")
        }
    }

    override fun onResume() {
        super.onResume()
        getAddressList()
    }

    private fun setupActionBar() {

        setSupportActionBar(toolbar_address_list_activity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
        }

        toolbar_address_list_activity.setNavigationOnClickListener { onBackPressed() }
    }

    private fun getAddressList() {
        val addressViewModel = ViewModelProvider(this).get(AddAddressViewModel::class.java)
        val mPrefs = getSharedPreferences("userToken", Context.MODE_PRIVATE)
        var userId = mPrefs.getString("userId", "").toString()
        val root = layoutInflater.inflate(R.layout.fragment_dashboard, container, false)
        addressViewModel.getAddresses(userId).observe(this, { address ->
            val adapter = this?.let { AddressListAdapter(this, address, true) }
            val rvAdress = root.findViewById<RecyclerView>(R.id.rv_dashboard_items)
            rvAdress.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            rvAdress?.adapter = adapter
            successAddressList(address)
        })
    }
    fun successAddressList(addressList: MutableList<DeliveryAddress>) {

        if (addressList.size > 0) {

            rv_address_list.visibility = View.VISIBLE
            tv_no_address_found.visibility = View.GONE

            rv_address_list.layoutManager = LinearLayoutManager(this@AddressListActivity)
            rv_address_list.setHasFixedSize(true)

            val addressAdapter = AddressListAdapter(this@AddressListActivity, addressList, mSelectAddress)
            rv_address_list.adapter = addressAdapter

            if (!mSelectAddress) {
                val editSwipeHandler = object : SwipeToEditCallback(this) {
                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                        val adapter = rv_address_list.adapter as AddressListAdapter
                        adapter.notifyEditItem(
                            this@AddressListActivity,
                            viewHolder.adapterPosition
                        )
                    }
                }
                val editItemTouchHelper = ItemTouchHelper(editSwipeHandler)
                editItemTouchHelper.attachToRecyclerView(rv_address_list)
            }
        } else {
            rv_address_list.visibility = View.GONE
            tv_no_address_found.visibility = View.VISIBLE
        }
    }
}