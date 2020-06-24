package com.app.dnadetec.activities

import android.content.Intent
import android.os.Build
import android.os.Handler
import android.view.View
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.app.dnadetec.App
import com.app.dnadetec.BuildConfig
import com.app.dnadetec.Global
import com.app.dnadetec.R
import com.app.dnadetec.bases.BaseActivity
import com.app.dnadetec.viewmodels.SettingActivityViewModel
import com.app.dnadetec.views.MessageDialog
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_setting.*
import javax.inject.Inject

class SettingActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: SettingActivityViewModel

    override fun getLayoutID(): Int = R.layout.activity_setting

    override fun onCreate() {

        App.appComponent.inject(this)
        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(SettingActivityViewModel::class.java)

        initView()

    }

    private fun initView() {

        textVersion.text = "version ${BuildConfig.VERSION_NAME}"

        buttonBack.setOnClickListener {

            finish()

        }

        textLang.text = when (Global.lang) {
            "en" -> {
                getString(R.string.text_english)
            }
            "th" -> {
                getString(R.string.text_thai)
            }
            else ->
                getString(R.string.text_english)
        }


        textLang.setOnClickListener {

            showSelectLangDialog()

        }

        textSignOut.setOnClickListener {

            showSignOutDialog()

        }

    }

    private fun showSignOutDialog() {

        showYesNoDialog(
            getString(R.string.text_sign_out_dialog),
            getString(R.string.text_yes),
            MessageDialog.CallBack {

                if (viewModel.signOut()) {
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }

            },
            getString(R.string.text_no),
            MessageDialog.CallBack {

            })

    }

    private fun showSelectLangDialog() {


        val bottomSheetView = layoutInflater.inflate(R.layout.bottom_sheet_layout, null)
        val bottomSheet = BottomSheetDialog(this)
        bottomSheet.setContentView(bottomSheetView)
        BottomSheetBehavior.from((bottomSheetView.parent) as View)

        val textEnglish = bottomSheet.findViewById<TextView>(R.id.textEnglish)
        val textThai = bottomSheet.findViewById<TextView>(R.id.textThai)

        textEnglish?.setOnClickListener {

            showProgressDialog()

            Handler().postDelayed({

                dismissProgressDialog()
                viewModel.changeLanguage(this,"en")
                textLang.text =
                    getString(R.string.text_english)
                bottomSheet.dismiss()

                goToMainPage()

            }, 1000)
        }

        textThai?.setOnClickListener {

            showProgressDialog()

            Handler().postDelayed({

                dismissProgressDialog()

                viewModel.changeLanguage(this,"th")
                textLang.text =
                    getString(R.string.text_thai)
                bottomSheet.dismiss()

                goToMainPage()

            }, 1000)

        }

        bottomSheet.show()

    }

    private fun goToMainPage() {

        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)

    }

}
