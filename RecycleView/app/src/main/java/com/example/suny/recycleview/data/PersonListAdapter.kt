package com.example.suny.recycleview.data

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.suny.recycleview.R
import com.example.suny.recycleview.model.Person

/**
 * Created by suny on 18/12/17.
 */

class PersonListAdapter(private val list: ArrayList<Person>,
                        private val context: Context) : RecyclerView.Adapter<PersonListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, position: Int): ViewHolder {
        //create our view from our xml file
        val view = LayoutInflater.from(context).inflate(R.layout.list_row, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindItem(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(person: Person) {
            var name: TextView = itemView.findViewById(R.id.name) as TextView
            var age: TextView = itemView.findViewById(R.id.age) as TextView

            name.text = person.name
            age.text = person.age.toString()

            itemView.setOnClickListener {

                Toast.makeText(context, name.text, Toast.LENGTH_LONG).show()
            }
        }
    }

}