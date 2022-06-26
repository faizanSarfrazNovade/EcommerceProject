package com.example.frd.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import com.example.frd.R
import com.example.frd.api.ApiClient
import com.example.frd.models.UserSignin
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.internal.http.HTTP_OK


class LoginActivity : BaseActivity(), View.OnClickListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login);
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        tv_register.setOnClickListener(){

            // Launch the register screen when the user clicks on the text.
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
        // Click event assigned to Forgot Password text.
        tv_forgot_password.setOnClickListener(this)
        // Click event assigned to Login button.
        btn_login.setOnClickListener(this)
        // Click event assigned to Register text.
        tv_register.setOnClickListener(this)
    }
    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {

                R.id.tv_forgot_password -> {
                    // Launch the forgot password screen when the user clicks on the forgot password text.
                    val intent = Intent(this@LoginActivity, ForgotPasswordActivity::class.java)
                    startActivity(intent)

                }

                R.id.btn_login -> {
                    logInRegisteredUser()
                }

                R.id.tv_register -> {
                    // Launch the register screen when the user clicks on the text.
                    val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }
    /**
     * A function to validate the login entries of a user.
     */
    private fun validateLoginDetails(): Boolean {
        val email = t_email.text.toString().trim()
        val password = t_password.text.toString().trim()
        return when {
            isValidEmail(email) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email), true)
                false
            };
            TextUtils.isEmpty(password) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_password), true)
                false
            }
            else -> {
                //showErrorSnackBar("Your Details are Valid",false)
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
    private fun logInRegisteredUser(){
        if(validateLoginDetails()){
            val email = t_email.text.toString().trim(){it<=' '}
            val password = t_password.text.toString().trim(){it<=' '}
            val signIn = UserSignin(email, password)
            val intent = Intent(this@LoginActivity, DashboardActivity::class.java)

            CoroutineScope(Dispatchers.IO).launch {
                val res = ApiClient.apiService.signIn(signIn)
                if (res.code() == HTTP_OK){
                    var userToken = res.body()!!
                    val preferences = getSharedPreferences("userToken", Context.MODE_PRIVATE)
                    val prefsEditor = preferences.edit()
                    prefsEditor.putString("userToken", userToken.accessToken)
                    prefsEditor.commit()
                    startActivity(intent)
                }else {
                    showErrorSnackBar(resources.getString(R.string.err_msg_email_password_mismatch), true)
                }
            }
        }
    }
}