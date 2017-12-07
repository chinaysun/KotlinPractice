package com.example.suny.hellokotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.CheckBox
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity(), OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var enteredText = nameEditText.text

        val performClick = showNameButton.setOnClickListener {
            resultLabel.text = "Welcome " + enteredText
        }

        // checkFruit.setOnClickListener(this) <- Set listner

    }

    fun onCheckBoxClicked( view: View) {

        view as CheckBox
        var isChecked: Boolean = view.isChecked

        when(view.id) {
            R.id.checkVeg -> if (isChecked) { Log.d("veggies","Veggies is check") } else { }
        }

        // if we use toString it means that the var assumes could be cast, be aware of this, because it could
        // crash the app, because it is not like Xcode could provide warning

    }

    // this is a delegate function, you need to set up listener onCreate
    override fun onClick(v: View?) {
        v as CheckBox
            var isChecked: Boolean = v.isChecked

            when (v.id) {

            }
    }

    // Kotlin do not support image + text button, what we do is to create a view that has click lisener.
    // setColorFilter ~= tintColor on swift
    // get random = Random().nextInt( put range here)
    // kotlin - linear layout ~= stack in swift - mechanism like auto layout, calculate one by one go back
    // to recalculate if needed
    // RelativeLayout is like what we write code in swift to layout the UI
    // RelativeLayout could not to set up constant which means it have to add many hidden view to layout the screen
}
