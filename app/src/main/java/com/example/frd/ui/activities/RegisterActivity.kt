package com.example.frd.ui.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.WindowManager
import android.widget.Toast
import com.example.frd.R
import com.example.frd.api.ApiClient
import com.example.frd.models.UserSignup
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.internal.http.HTTP_OK


class RegisterActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        @Suppress("DEPRECATION")
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        tv_login.setOnClickListener(){

            // Launch the register screen when the user clicks on the text.
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
        }
        setupActionBar()

        btn_register.setOnClickListener {
            registerUser()
        }
    }
    private fun setupActionBar() {

        setSupportActionBar(toolbar_register_activity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_new_24)
        }

        toolbar_register_activity.setNavigationOnClickListener { onBackPressed() }
    }

    /**
     * A function to validate the entries of a new user.
     */
    private fun validateRegisterDetails(): Boolean {
        return when {

            TextUtils.isEmpty(et_first_name.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_first_name), true)
                false
            }

            TextUtils.isEmpty(et_last_name.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_last_name), true)
                false
            }

            isValidEmail(et_email.text.toString().trim()) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email), true)
                false
            }

            TextUtils.isEmpty(et_password.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_password), true)
                false
            }

            TextUtils.isEmpty(et_confirm_password.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_confirm_password), true)
                false
            }

            et_password.text.toString().trim { it <= ' ' } != et_confirm_password.text.toString()
                .trim { it <= ' ' } -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_password_and_confirm_password_mismatch), true)
                false
            }
            !cb_terms_and_condition.isChecked -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_agree_terms_and_condition), true)
                false
            }
            else -> {
                true
            }
        }
    }
    fun isValidEmail(target: CharSequence?): Boolean {
        return if (TextUtils.isEmpty(target)) {
            false
        } else {
            !Patterns.EMAIL_ADDRESS.matcher(target).matches()
        }
    }
    private fun registerUser() {

        // Check with validate function if the entries are valid or not.
        if (validateRegisterDetails()) {

            val email: String = et_email.text.toString().trim { it <= ' ' }
            val password: String = et_password.text.toString().trim { it <= ' ' }
            val name: String = et_first_name.text.toString().trim { it <= ' ' }
            val lastName: String = et_last_name.text.toString().trim { it <= ' ' }
            val age: Int = 0
            val civility= null
            val admin= null
            val signUp = UserSignup(email, password)
            CoroutineScope(Dispatchers.IO).launch {
                val res = ApiClient.apiService.signUp(signUp)

                if (res.code() == HTTP_OK){
                    Toast.makeText(applicationContext, "Un compte a été crée", Toast.LENGTH_LONG ).show()
                }else {
                    Toast.makeText(applicationContext, "tu joues a quoi", Toast.LENGTH_LONG ).show()
                }
            }
        }
    }

}