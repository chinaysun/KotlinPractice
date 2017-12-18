package com.example.suny.choreapp.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.suny.choreapp.R
import com.example.suny.choreapp.data.ChoreListAdapter
import com.example.suny.choreapp.model.Chore
import kotlinx.android.synthetic.main.activity_chore_list.*

class ChoreListActivity : AppCompatActivity() {

    private var adapter: ChoreListAdapter? = null
    private var choreList: ArrayList<Chore>? = null
    private var layoutManager: RecyclerView.LayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chore_list)

        choreList = ArrayList<Chore>()
        layoutManager = LinearLayoutManager(this)
        adapter = ChoreListAdapter(choreList!!, this)


        recyclerViewId.layoutManager = layoutManager
        recyclerViewId.adapter = adapter
    }

}
