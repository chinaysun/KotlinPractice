package com.example.suny.listview

import android.content.Context
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listView = findViewById<ListView>(R.id.main_listview)

        // custom adapter telling my list what to display
        listView.adapter = MyCustomAdapter(this)

        listView.setOnItemClickListener { parent, view, position, id ->

            var itemName: String = listView.getItemAtPosition(position) as String
            Toast.makeText(this,"ID: $id, Name: + $itemName",Toast.LENGTH_LONG).show()

        }
    }

    private class MyCustomAdapter(context: Context): BaseAdapter() {

        private val mContext: Context
        private val names = arrayListOf<String>(
                "Donald Trump", "Steve Jobs", "Tim Cook"
        )

        init {
            mContext = context
        }

        //responsible for how many rows in my list
        override fun getCount(): Int {
            return names.size
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getItem(position: Int): String {
            return names.get(position)
        }

        // responsible for rendering out each row
        override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View {
            val layoutInflater = LayoutInflater.from(mContext)
            var rowMain = layoutInflater.inflate(R.layout.row_main, viewGroup, false)

            val positionTextView = rowMain.findViewById<TextView>(R.id.position_textview)
            positionTextView.text = "Row number: $position"

            val nameTextView = rowMain.findViewById<TextView>(R.id.name_textview)
            nameTextView.text = names.get(position)

            return rowMain

        }
    }
}
