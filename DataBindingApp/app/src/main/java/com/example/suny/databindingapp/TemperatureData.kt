package com.example.suny.databindingapp

import android.databinding.BaseObservable
import android.databinding.Bindable

/**
 * Created by suny on 5/1/18.
 */
class TemperatureData(private var location: String? = null,
                      private var celsius: String? = null): BaseObservable() {
    @Bindable
    fun getCelsius(): String? {
        return celsius
    }

    @Bindable
    fun getLocation(): String? {
        return location
    }

    fun setLocation(location: String) {
        this.location = location
        notifyPropertyChanged(BR.location)
    }

    fun setCelsius(celsius: String) {
        this.celsius = celsius
        notifyPropertyChanged(BR.celsius)
    }

}