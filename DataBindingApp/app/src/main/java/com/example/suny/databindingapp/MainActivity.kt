package com.example.suny.databindingapp

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.suny.databindingapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), MainActivityContract.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this,R.layout.activity_main)
        val mainActivityPresenter = MainActivityPresenter(this,applicationContext)
        val temperatureData = TemperatureData("Melbourne","28")
        binding.temp = temperatureData
        binding.presenter = mainActivityPresenter
    }

    override fun showData(temperatureData: TemperatureData) {
        val celsius = temperatureData.getCelsius()
        Toast.makeText(this, celsius, Toast.LENGTH_SHORT).show()
    }


}
