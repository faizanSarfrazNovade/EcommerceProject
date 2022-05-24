package com.example.frd.ui.activities

import android.os.Bundle
import com.example.frd.R
import kotlinx.android.synthetic.main.activity_forgot_password.*

class ForgotPasswordActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        setupActionBar()
        btn_submit.setOnClickListener {

            // Get the email id from the input field.
            val email: String = et_email.text.toString().trim { it <= ' ' }

            // Now, If the email entered in blank then show the error message or else continue with the implemented feature.
            if (email.isEmpty()) {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email), true)
            } else {
                // Show the progress dialog.
                showProgressDialog(resources.getString(R.string.please_wait))
                hideProgressDialog()
                // This piece of code is used to send the reset password link to the user's email id if the user is registered.
            }
        }
    }
    private fun setupActionBar() {

        setSupportActionBar(toolbar_forgot_password_activity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_new_24)
            actionBar.title = ""
        }

        toolbar_forgot_password_activity.setNavigationOnClickListener { onBackPressed() }
    }
}