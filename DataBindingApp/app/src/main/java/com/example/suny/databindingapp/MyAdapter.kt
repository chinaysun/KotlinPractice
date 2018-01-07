package com.example.suny.databindingapp

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.content.Context
import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.ViewGroup

/**
 * Created by suny on 8/1/18.
 */

class MyAdapter(private val data:List<TemperatureData>,
                private val context:Context): RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ViewDataBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(obj: Any) {
            binding.setVariable(BR.obj, obj)
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {

        val layoutInflater = LayoutInflater.from(context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(layoutInflater,
                R.layout.rowlayout,
                parent,
                false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val temperatureData = data.get(index = position)
        holder.bind(temperatureData)
    }

    override fun getItemCount(): Int {
        return data.size
    }

}