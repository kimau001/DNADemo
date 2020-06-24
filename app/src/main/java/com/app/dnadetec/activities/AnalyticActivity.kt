package com.app.dnadetec.activities

import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.app.dnadetec.App
import com.app.dnadetec.R
import com.app.dnadetec.bases.BaseActivity
import com.app.dnadetec.models.AnalyticData
import com.app.dnadetec.responses.Result
import com.app.dnadetec.viewmodels.AnalyticActivityViewModel
import com.app.dnadetec.views.MessageDialog
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_analytic.*
import mobi.upod.timedurationpicker.TimeDurationPicker
import javax.inject.Inject

class AnalyticActivity : BaseActivity() {


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: AnalyticActivityViewModel


    override fun getLayoutID(): Int = R.layout.activity_analytic

    override fun onCreate() {

        App.appComponent.inject(this)

        viewModel =
            ViewModelProviders.of(this,viewModelFactory).get(AnalyticActivityViewModel::class.java)

        initView()

    }

    private fun initView() {

        buttonBack.setOnClickListener {

            finish()

        }

        buttonConfirm.setOnClickListener {

            val resultValidation = viewModel.dataValidate( editProjectName.text.toString(),
                editLocation.text.toString(),
                editStem.text.toString(),
                editProjectDetail.text.toString(),
                editTemperature.text.toString(),
                editTime.text.toString())

            if (resultValidation.isSuccess){

                showAddAnalyticDialog(resultValidation)

            }else{
                showDialog(resultValidation.message.toString(),getString(R.string.text_close))
            }

        }

        editTime.setOnClickListener {

            showSelectTimerDuration()

        }

        debug()

    }

    private fun debug() {
        // TODO for debug

        editProjectName.setText("test")
        editLocation.setText("test")
        editStem.setText("test")
        editProjectDetail.setText("test")
        editTemperature.setText("test")

    }

    private fun showAddAnalyticDialog(resultValidation: Result<AnalyticData>) {

        showYesNoDialog(
            "Please confirm your analytic details.",
            getString(R.string.text_confirm),
            MessageDialog.CallBack {

              gotoTimer(resultValidation)

            },
            getString(R.string.text_cancel),
            MessageDialog.CallBack {

            })

    }

    private fun gotoTimer(resultValidation: Result<AnalyticData>) {

        //TODO go to timer page

        val intent = Intent(this,TimerActivity::class.java)
        intent.putExtra("data",resultValidation.data)
        startActivity(intent)


    }

    private fun addAnalytic() {

        showProgressDialog()

        viewModel.addAnalytic(
            editProjectName.text.toString(),
            editLocation.text.toString(),
            editStem.text.toString(),
            editProjectDetail.text.toString(),
            editTemperature.text.toString()
        ).observe(this, Observer {

            dismissProgressDialog()

            if (it.isSuccess){



            }else{

                showDialog(it.message.toString(),getString(R.string.text_close))

            }

        })

    }

    private fun showSelectTimerDuration() {

        val bottomSheetView = layoutInflater.inflate(R.layout.bottom_time_duration_picker_layout, null)
        val bottomSheet = BottomSheetDialog(this)
        bottomSheet.setContentView(bottomSheetView)
        BottomSheetBehavior.from((bottomSheetView.parent) as View)

        val timeDurationInput = bottomSheetView.findViewById<TimeDurationPicker>(R.id.timeDurationInput)

        timeDurationInput.duration =  viewModel.duration

        val textConfirm = bottomSheetView.findViewById<TextView>(R.id.textConfirm)
        textConfirm.setOnClickListener {

            viewModel.duration = timeDurationInput.duration

            editTime.setText(viewModel.getFormatedDuration())
            bottomSheet.dismiss()

        }

        val textCancel = bottomSheetView.findViewById<TextView>(R.id.textCancel)
        textCancel.setOnClickListener {

            bottomSheet.dismiss()

        }

        bottomSheet.show()

    }

}
