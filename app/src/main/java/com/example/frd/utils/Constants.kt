package com.example.frd.utils

import android.app.Activity
import android.content.Intent
import android.provider.MediaStore

/**
 * A custom object to declare all the constant values in a single file. The constant values declared here is can be used in whole application.
 */
object Constants {

    const val FRD_PREFERENCES: String = "FRDPrefs"
    const val LOGGED_IN_USERNAME: String = "logged_in_username"
    const val READ_STORAGE_PERMISSION_CODE = 2
    const val PICK_IMAGE_REQUEST_CODE = 2
    const val EXTRA_PRODUCT_ID: String = "extra_product_id"
    const val EXTRA_ADDRESS_DETAILS: String = "AddressDetails"
    const val CART_QUANTITY: String = "cart_quantity"
    const val EXTRA_SELECT_ADDRESS: String = "extra_select_address"
    const val ADD_ADDRESS_REQUEST_CODE: Int = 121
    const val EXTRA_MY_ORDER_DETAILS: String = "extra_MY_ORDER_DETAILS"


    /**
     * A function for user profile image selection from phone storage.
     */
    fun showImageChooser(activity: Activity) {
        // An intent for launching the image selection of phone storage.
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        // Launches the image selection of phone storage using the constant code.
        activity.startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST_CODE)
    }

}