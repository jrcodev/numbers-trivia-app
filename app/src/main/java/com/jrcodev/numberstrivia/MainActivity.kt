package com.jrcodev.numberstrivia

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.jrcodev.numberstrivia.databinding.ActivityMainBinding
import com.jrcodev.numberstrivia.ui.NumberTriviaItemAdapter


class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private val listAdapter = NumberTriviaItemAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(
            this,
            MainViewModelFactory(application)
        ).get(MainViewModel::class.java)
        setContentView(binding.root)

        binding.searchButton.setOnClickListener {
            val number = binding.editNumber.text.toString()
            binding.editNumber.text.clear()
            if (number.isNotEmpty()) {
                viewModel.getNumberTrivia(number)
            }
        }

        viewModel.searchStatus.observe(this, Observer {
            it?.let {
                when (it) {
                    is SearchStatus.Result -> {
                        binding.viewStatusSearch.status = Status.Result
                        binding.viewStatusSearch.label = it.data
                        viewModel.insertTrivia(it.data.createNumberTrivia())
                    }
                    is SearchStatus.Error -> {
                        binding.viewStatusSearch.status = Status.Error
                    }
                    is SearchStatus.Start -> {
                        binding.viewStatusSearch.status = Status.Start
                    }
                    else -> {
                        binding.viewStatusSearch.status = Status.Loading
                    }
                }
            }
        })

        viewModel.triviaList.observe(this, Observer {
            listAdapter.data = it
        })

        binding.triviaList.adapter = listAdapter


        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                viewModel.deleteTrivia(listAdapter.getTriviaAt(viewHolder.adapterPosition))
                listAdapter.notifyItemRemoved(viewHolder.adapterPosition)
            }

        }).attachToRecyclerView(binding.triviaList)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.shuffle_action) {
            binding.editNumber.text.clear()
            viewModel.getRandomTrivia()
        }
        return super.onOptionsItemSelected(item)
    }
}