package com.example.suny.databindingapp

/**
 * Created by suny on 5/1/18.
 */
interface MainActivityContract {

    interface Presenter {
        fun onShowData(temperatureData: TemperatureData)
        fun showList()
    }

    interface View {
        fun showData(temperatureData: TemperatureData)
    }

}