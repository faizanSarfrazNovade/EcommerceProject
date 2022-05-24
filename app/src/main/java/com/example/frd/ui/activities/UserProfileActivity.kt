package com.example.frd.ui.activities

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.frd.R
import com.example.frd.models.User
import com.example.frd.utils.Constants
import com.example.frd.utils.GlideLoader
import kotlinx.android.synthetic.main.activity_user_profile.*
import java.io.IOException

class UserProfileActivity : BaseActivity(), View.OnClickListener {

    private lateinit var mUserDetails: User
    private var mSelectedImageFileUri: Uri? = null
    private var mUserProfileImageURL: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)
        // Create a instance of the User model class.
//        var userDetails: User = User()
//        if(intent.hasExtra(Constants.EXTRA_USER_DETAILS)) {
//            // Get the user details from intent as a ParcelableExtra.
//            mUserDetails = intent.getParcelableExtra(Constants.EXTRA_USER_DETAILS)!!
//        }

        // If the profile is incomplete then user is from login screen and wants to complete the profile.
//        if (mUserDetails.profileCompleted == 0) {
//            // Update the title of the screen to complete profile.
//            tv_title.text = resources.getString(R.string.title_complete_profile)
//
//            // Here, the some of the edittext components are disabled because it is added at a time of Registration.
//            et_first_name.isEnabled = false
//            et_first_name.setText(mUserDetails.firstName)
//
//            et_last_name.isEnabled = false
//            et_last_name.setText(mUserDetails.lastName)
//
//            et_email.isEnabled = false
//            et_email.setText(mUserDetails.email)
//        } else {
//
//            // Call the setup action bar function.
//            setupActionBar()
//
//            // Update the title of the screen to edit profile.
//            tv_title.text = resources.getString(R.string.title_edit_profile)
//
//            // Load the image using the GlideLoader class with the use of Glide Library.
//            GlideLoader(this@UserProfileActivity).loadUserPicture(mUserDetails.image, iv_user_photo)
//
//            // Set the existing values to the UI and allow user to edit except the Email ID.
//            et_first_name.setText(mUserDetails.firstName)
//            et_last_name.setText(mUserDetails.lastName)
//
//            et_email.isEnabled = false
//            et_email.setText(mUserDetails.email)
//
//            if (mUserDetails.mobile != 0L) {
//                et_mobile_number.setText(mUserDetails.mobile.toString())
//            }
//            if (mUserDetails.gender == Constants.MALE) {
//                rb_male.isChecked = true
//            } else {
//                rb_female.isChecked = true
//            }
//        }
        // Assign the on click event to the user profile photo.
        iv_user_photo.setOnClickListener(this@UserProfileActivity)
        btn_save.setOnClickListener(this@UserProfileActivity)
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {

                R.id.iv_user_photo -> {

                    // Here we will check if the permission is already allowed or we need to request for it.
                    // First of all we will check the READ_EXTERNAL_STORAGE permission and if it is not allowed we will request for the same.
                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        )
                        == PackageManager.PERMISSION_GRANTED
                    ) {

                        //showErrorSnackBar("You already have the storage permission.", false)
                        Constants.showImageChooser(this@UserProfileActivity)
                    } else {

                        /*Requests permissions to be granted to this application. These permissions
                         must be requested in your manifest, they should not be granted to your app,
                         and they should have protection level*/

                        ActivityCompat.requestPermissions(
                            this,
                            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                            Constants.READ_STORAGE_PERMISSION_CODE
                        )
                    }
                }

                R.id.btn_save ->{
                    if (validateUserProfileDetails()) {

                        // Show the progress dialog.
                        showProgressDialog(resources.getString(R.string.please_wait))
                        hideProgressDialog()
                    }
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constants.READ_STORAGE_PERMISSION_CODE) {
            //If permission is granted
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                //showErrorSnackBar("The storage permission is granted.", false)
                Constants.showImageChooser(this@UserProfileActivity)
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(
                    this,
                    resources.getString(R.string.read_storage_permission_denied),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Constants.PICK_IMAGE_REQUEST_CODE) {
                if (data != null) {
                    try {
                        // The uri of selected image from phone storage.
                        mSelectedImageFileUri = data.data!!

                        GlideLoader(this@UserProfileActivity).loadUserPicture(
                                mSelectedImageFileUri!!,
                                iv_user_photo
                        )

                        //iv_user_photo.setImageURI(Uri.parse(selectedImageFileUri.toString()))
                        GlideLoader(this).loadUserPicture(mSelectedImageFileUri!!,iv_user_photo)
                    } catch (e: IOException) {
                        e.printStackTrace()
                        Toast.makeText(
                            this@UserProfileActivity,
                            resources.getString(R.string.image_selection_failed),
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            // A log is printed when user close or cancel the image selection.
            Log.e("Request Cancelled", "Image selection cancelled")
        }
    }


    /**
     * A function to validate the input entries for profile details.
     */
    private fun validateUserProfileDetails(): Boolean {
        return when {

            // We have kept the user profile picture is optional.
            // The FirstName, LastName, and Email Id are not editable when they come from the login screen.
            // The Radio button for Gender always has the default selected value.

            // Check if the mobile number is not empty as it is mandatory to enter.
            TextUtils.isEmpty(et_mobile_number.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_mobile_number), true)
                false
            }
            else -> {
                true
            }
        }
    }
}