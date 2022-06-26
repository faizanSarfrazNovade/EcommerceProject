package com.example.frd.ui.activities

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import com.example.frd.R
import com.example.frd.api.ApiClient
import com.example.frd.models.DeliveryAddress
import kotlinx.android.synthetic.main.activity_add_edit_address.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddEditAddressActivity : BaseActivity() {

    private var mAddressDetails: DeliveryAddress? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_address)

        setupActionBar()



        if (mAddressDetails != null) {

                tv_title1.text = resources.getString(R.string.title_edit_address)
                btn_submit_address.text = resources.getString(R.string.btn_lbl_update)

                et_street.setText(mAddressDetails?.street)
                et_postal_code.setText(mAddressDetails?.zipCode.toString())

        }
        btn_submit_address.setOnClickListener {
            saveAddress()
        }
    }

    private fun setupActionBar() {

        setSupportActionBar(toolbar_add_edit_address_activity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
        }

        toolbar_add_edit_address_activity.setNavigationOnClickListener { onBackPressed() }
    }

    private fun validateData(): Boolean {
        return when {

            TextUtils.isEmpty(et_street.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_please_enter_address), true)
                false
            }

            TextUtils.isEmpty(et_postal_code.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_please_enter_zip_code), true)
                false
            }

            else -> {
                true
            }
        }
    }

    private fun saveAddress() {

        val country: String = et_country.text.toString().trim { it <= ' ' }
        val city: String = et_city.text.toString().trim { it <= ' ' }
        val street: String = et_street.text.toString().trim { it <= ' ' }
        val zipCode: String = et_postal_code.text.toString().trim { it <= ' ' }
        if (validateData()) {
            val mPrefs = getSharedPreferences("userToken", Context.MODE_PRIVATE)
            var userId = mPrefs.getString("userId", "").toString()
            val addressModel = DeliveryAddress(
                country,
                street,
                zipCode.toInt(),
                city,
                userId
            )
            CoroutineScope(Dispatchers.IO).launch {
                ApiClient.apiService.addAddress(addressModel)
            }
        }
    }
}