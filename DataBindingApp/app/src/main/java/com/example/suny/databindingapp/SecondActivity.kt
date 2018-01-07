package com.example.suny.databindingapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_second.*
import java.util.*
import java.util.Arrays.asList



class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        my_recycler_view.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(this)
        my_recycler_view.layoutManager = layoutManager

        val items = Arrays.asList(TemperatureData("Hamburg", "5"), TemperatureData("Berlin", "6"))

        // define an adapter
        val mAdapter = MyAdapter(items,this)
        my_recycler_view.setAdapter(mAdapter)


    }
}
