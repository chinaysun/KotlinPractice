package com.example.suny.choreapp.data

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.suny.choreapp.R
import com.example.suny.choreapp.model.Chore

class ChoreListAdapter(private val list: ArrayList<Chore>,
                       private val context: Context): RecyclerView.Adapter<ChoreListAdapter.ViewHolder>() {
    override fun getItemCount(): Int {
       return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindViews(list[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_row, parent, false)

        return ViewHolder(view)
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        var choreName = itemView.findViewById<TextView>(R.id.listChoreName)
        var assignBy = itemView.findViewById<TextView>(R.id.listAssignedBy)
        var assignDate = itemView.findViewById<TextView>(R.id.listDate)
        var assignTo = itemView.findViewById<TextView>(R.id.assignToId)

        fun bindViews(chore: Chore) {
            choreName.text = chore.choreName
            assignBy.text = chore.assignedBy
            assignTo.text = chore.assignedTo
            assignDate.text = chore.showHumanDate(chore.timeAssigned!!)
        }


    }


}