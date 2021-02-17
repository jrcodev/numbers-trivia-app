package com.jrcodev.numberstrivia.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.jrcodev.numberstrivia.R
import com.jrcodev.numberstrivia.databinding.NumberItemTriviaBinding
import com.jrcodev.numberstrivia.storage.NumberTrivia

class NumberTriviaItemAdapter : RecyclerView.Adapter<NumberTriviaItemAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = NumberItemTriviaBinding.bind(itemView)

        fun bind(trivia: NumberTrivia) {
            binding.highTitle.text = trivia.number
            binding.description.text = trivia.desc
        }
    }

    var data = listOf<NumberTrivia>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.number_item_trivia, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val trivia = data[position]
        holder.bind(trivia)
    }

    fun getTriviaAt(pos: Int) = data[pos]
}