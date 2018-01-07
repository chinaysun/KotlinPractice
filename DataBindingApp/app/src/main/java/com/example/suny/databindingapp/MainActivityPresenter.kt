package com.example.suny.databindingapp

import android.content.Context

/**
 * Created by suny on 5/1/18.
 */
class MainActivityPresenter(val view:MainActivityContract.View,
                            val context: Context) : MainActivityContract.Presenter {

    override fun onShowData(temperatureData: TemperatureData) {
        view.showData(temperatureData)
    }
}