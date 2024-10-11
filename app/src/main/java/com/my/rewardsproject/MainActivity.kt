package com.my.rewardsproject

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.my.rewardsproject.adapters.ItemAdapter
import com.my.rewardsproject.business.AppViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val appViewModel: AppViewModel by viewModels()
    private lateinit var adapter: ItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val recyclerView = findViewById<RecyclerView>(R.id.rv)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val errorTextView = findViewById<TextView>(R.id.errorTextView)

        // Set up RecyclerView with LinearLayoutManager and adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Observe the rewards state
        lifecycleScope.launchWhenStarted {
            appViewModel.rewardFlow.collect { state ->
                when (state) {
                    is AppViewModel.UiStates.INITIAL -> {
                        recyclerView.visibility = View.GONE
                        progressBar.visibility = View.GONE
                        errorTextView.visibility = View.GONE
                    }
                    is AppViewModel.UiStates.LOADING -> {
                        progressBar.visibility = View.VISIBLE
                        recyclerView.visibility = View.GONE
                        errorTextView.visibility = View.GONE
                    }
                    is AppViewModel.UiStates.SUCCESS -> {
                        progressBar.visibility = View.GONE
                        recyclerView.visibility = View.VISIBLE
                        errorTextView.visibility = View.GONE

                        // Grouped data is received here, so flatten it for display in RecyclerView
                        val flattenedList = state.groupedRewards.flatMap { it.value }

                        // Initialize adapter with the grouped and sorted list
                        adapter = ItemAdapter(flattenedList)
                        recyclerView.adapter = adapter
                    }
                    is AppViewModel.UiStates.ERROR -> {
                        progressBar.visibility = View.GONE
                        recyclerView.visibility = View.GONE
                        errorTextView.visibility = View.VISIBLE
                        errorTextView.text = state.error
                    }
                }
            }
        }

        // Trigger the ViewModel to fetch data
        appViewModel.getRewards()
    }
}