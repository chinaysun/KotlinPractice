package com.example.suny.databindingapp

import android.content.Context
import android.content.Intent

/**
 * Created by suny on 5/1/18.
 */
class MainActivityPresenter(val view:MainActivityContract.View,
                            val context: Context) : MainActivityContract.Presenter {

    override fun onShowData(temperatureData: TemperatureData) {
        view.showData(temperatureData)
    }

    override fun showList() {
        val intent = Intent(context,SecondActivity::class.java)
        context.startActivity(intent)
    }
}