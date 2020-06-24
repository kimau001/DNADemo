package com.app.dnadetec.adapters

import android.content.Context
import android.os.Build
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.app.dnadetec.R
import com.app.dnadetec.models.AnalyticData
import com.bumptech.glide.Glide

class ResultListViewAdapter(
    val context: Context,
    private val listDao: List<AnalyticData>,
    private val callBack : CallBack
) :
    RecyclerView.Adapter<ResultListViewAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(position, listDao[position])
        holder.itemView.setOnClickListener {
            callBack?.onClick(position, listDao[position])
        }
    }

    override fun getItemCount(): Int {
        return listDao.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : ViewHolder {

        val itemView: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_analytic, parent, false)

        return ViewHolder(itemView)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindItems(
            position: Int,
            analyticData: AnalyticData
        ) {
            val textName = itemView.findViewById(R.id.textName) as TextView
            val textDetails = itemView.findViewById(R.id.textDetails) as TextView
            val textDate = itemView.findViewById(R.id.textDate) as TextView
            val textCheck = itemView.findViewById(R.id.textCheck) as TextView
            val imageView = itemView.findViewById(R.id.imageView) as ImageView
            val textResultPositive = itemView.findViewById(R.id.textResultPositive) as TextView

            textName.text = analyticData.name
            textDetails.text = analyticData.detail
            textDate.text = analyticData.date

            Glide.with(imageView).load(analyticData.imgDNA).into(imageView)

            if (analyticData.result.isNullOrEmpty()){

                textCheck.text = textCheck.resources.getString(R.string.text_check_waiting)
                setTextColorGray(textCheck)
                textResultPositive.text = textCheck.resources.getString(R.string.text_result_empty)
                setTextColorGray(textResultPositive)
                textResultPositive.text = ""

            }else{

                when(analyticData.check){
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

                when(analyticData.result){
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

    interface CallBack {
        fun onClick(
            position: Int,
            analyticData: AnalyticData
        )
    }

}