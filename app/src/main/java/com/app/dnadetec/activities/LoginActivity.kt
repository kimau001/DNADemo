package com.app.dnadetec.activities

import android.content.Intent
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.KeyEvent
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.app.dnadetec.App
import com.app.dnadetec.R
import com.app.dnadetec.bases.BaseActivity
import com.app.dnadetec.viewmodels.LoginActivityViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: LoginActivityViewModel

    private var isShowPassword = false

    override fun getLayoutID(): Int = R.layout.activity_login

    override fun onCreate() {

        App.appComponent.inject(this)

        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(LoginActivityViewModel::class.java)

        initView()

    }

    private fun initView() {

        imageViewPassword.setOnClickListener {

            if (isShowPassword) {

                isShowPassword = false
                imageViewPassword.setImageResource(R.drawable.baseline_visibility_black_24)
                editPassword.transformationMethod = PasswordTransformationMethod.getInstance()

            } else {

                isShowPassword = true
                imageViewPassword.setImageResource(R.drawable.baseline_visibility_off_black_24)
                editPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()

            }

        }

        editPassword.setOnKeyListener { v, keyCode, event ->
            if ((event?.action == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                signIn(editUsername.text.toString(), editPassword.text.toString())
                true
            }
            false
        }

        llSignin.setOnClickListener {

            signIn(editUsername.text.toString(), editPassword.text.toString())

        }

        textSignUp.setOnClickListener {

            val intent = Intent(this,SignUpActivity::class.java)
            startActivity(intent)
        }

    }

    private fun signIn(username: String, password: String) {

        showProgressDialog()

        viewModel.signIn(username, password)?.observe(this, Observer {

            dismissProgressDialog()

            if (it.isSuccess) {

                // TODO goto main page

                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
                finish()

            } else {

                Snackbar.make(
                    findViewById(android.R.id.content),
                    it.message.toString(),
                    Snackbar.LENGTH_SHORT
                ).show()

            }

        })


    }

}
