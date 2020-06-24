package com.app.dnadetec.activities

import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.dnadetec.App
import com.app.dnadetec.R
import com.app.dnadetec.adapters.ResultListViewAdapter
import com.app.dnadetec.bases.BaseActivity
import com.app.dnadetec.models.AnalyticData
import com.app.dnadetec.viewmodels.ResultListActivityViewModel
import kotlinx.android.synthetic.main.activity_result_list.*
import javax.inject.Inject

class ResultListActivity : BaseActivity() {

    lateinit var callBack : ResultListViewAdapter.CallBack

    private var isSwipe = false

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: ResultListActivityViewModel

    override fun getLayoutID(): Int = R.layout.activity_result_list

    override fun onCreate() {

        integrateViewModel()
        initView()
        getAllAnalytic()

    }

    private fun getAllAnalytic() {

        if (!isSwipe){
            showProgressDialog()
        }

        viewModel.getAllAnalytic().observe(this, Observer {

            if (isSwipe){
                recyclerView.suppressLayout(false)
                swipeRefreshLayout.isRefreshing = false
                isSwipe = false
            }else{
                dismissProgressDialog()
            }

            if (it.isSuccess){

                initAdapter(it.data!!)

            }else{
                showDialog(it.message.toString(),resources.getString(R.string.text_close))
            }

        })

    }

    private fun integrateViewModel() {

        App.appComponent.inject(this)

        viewModel =
            ViewModelProviders.of(this,viewModelFactory).get(ResultListActivityViewModel::class.java)

    }

    private fun initView() {


        buttonBack.setOnClickListener {

            finish()

        }


        swipeRefreshLayout.setOnRefreshListener {
            if (!isSwipe) {
                isSwipe = true

                recyclerView.suppressLayout(true)
                getAllAnalytic()

            }
        }


    }

    private fun initAdapter(data: List<AnalyticData>) {

        callBack = object : ResultListViewAdapter.CallBack {
            override fun onClick(position: Int, analyticData: AnalyticData) {

                val intent = Intent(mActivity,ResultDetailActivity::class.java)
                intent.putExtra("data",analyticData)
                startActivity(intent)

            }

        }

        recyclerView.also {
            it.adapter = ResultListViewAdapter(this,data,callBack)
            it.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        }

    }

}
