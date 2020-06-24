package com.app.dnadetec.activities

import android.os.Build
import android.widget.TextView
import com.app.dnadetec.R
import com.app.dnadetec.bases.BaseActivity
import com.app.dnadetec.models.AnalyticData
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_result_detail.*
import kotlinx.android.synthetic.main.activity_result_detail.textCheck
import kotlinx.android.synthetic.main.activity_result_detail.textDate
import kotlinx.android.synthetic.main.activity_result_detail.textName

class ResultDetailActivity : BaseActivity() {

    var analyticData : AnalyticData? = null

    override fun getLayoutID(): Int = R.layout.activity_result_detail

    override fun onCreate() {

        getIntentData()
        initView()
    }

    private fun getIntentData() {

        if (intent.extras != null) {
            analyticData = intent.extras?.getParcelable("data")
        }

    }

    private fun initView() {

        buttonBack.setOnClickListener {
            finish()
        }

        textName.text = analyticData?.name
        textDate.text = analyticData?.date
        textPlace.text = analyticData?.place
        textStem.text = analyticData?.disease
        textTemp.text = analyticData?.temp + " °C"
        textDetail.text = analyticData?.detail
        textTime.text = analyticData?.getTimeText(this)

        Glide.with(this).load(analyticData?.imgDNA).into(imageView)

        if (analyticData?.result.isNullOrEmpty()){

            textCheck.text = textCheck.resources.getString(R.string.text_check_waiting)
            setTextColorGray(textCheck)
            textResultPositive.text = textCheck.resources.getString(R.string.text_result_empty)
            setTextColorGray(textResultPositive)
            textResultPositive.text = ""

        }else{

            when(analyticData?.check){
                "1"->{
                    textCheck.text = textCheck.resources.getString(R.string.text_result_positive)
                    setTextColorGreen(textCheck)
                    setTextColorGreen(textResultPositive)
                }
                else->{
                    textCheck.text = textCheck.resources.getString(R.string.text_result_negative)
                    setTextColorRed(textCheck)
                    setTextColorRed(textResultPositive)
                }
            }

            when(analyticData?.result){
                "0"->{
                    textResultPositive.text = ""
                }
                "1"->{
                    textResultPositive.text = "•"
                }
                "2"->{
                    textResultPositive.text = "• •"
                }
                "3"->{
                    textResultPositive.text = "• • •"
                }
                else->{
                    textResultPositive.text = ""
                }
            }

        }

    }


    private fun setTextColorRed(textView: TextView){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            textView.setTextColor(textView.resources.getColor(R.color.colorRed,null))
        }else{
            textView.setTextColor(textView.resources.getColor(R.color.colorRed))
        }
    }

    private fun setTextColorGreen(textView: TextView){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            textView.setTextColor(textView.resources.getColor(R.color.colorGreen,null))
        }else{
            textView.setTextColor(textView.resources.getColor(R.color.colorGreen))
        }
    }

    private fun setTextColorGray(textView: TextView){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            textView.setTextColor(textView.resources.getColor(R.color.colorGray,null))
        }else{
            textView.setTextColor(textView.resources.getColor(R.color.colorGray))
        }
    }

}
