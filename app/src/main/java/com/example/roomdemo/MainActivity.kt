package com.example.roomdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomdemo.databinding.ActivityMainBinding
import com.example.roomdemo.db.Subscriber
import com.example.roomdemo.db.SubscriberDatabase
import com.example.roomdemo.db.SubscriberRepository
import com.example.roomdemo.viiewmodel.SubscriberViewModel
import com.example.roomdemo.viiewmodel.SubscriberViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var subscriberViewModel: SubscriberViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        //create a DAO instance
        val dao = SubscriberDatabase.getInstance(application).subscriberDAO
        val repository = SubscriberRepository(dao)
        val factory = SubscriberViewModelFactory(repository)
        subscriberViewModel = ViewModelProvider(this,factory).get(SubscriberViewModel::class.java)

        //assign the view model instance to the data-binding object
        binding.myViewModel = subscriberViewModel
        binding.lifecycleOwner = this
        initRecyclerView()

    }
    //function to observer list of subscriber data in the DB table
    private fun displaySubscriberList() {
        subscriberViewModel.getSaveSubscribers().observe(this,{
            Log.i("Room", it.toString())
            binding.subscriberRecyclerView.adapter = MyRecyclerViewAdapter(
                it
            ) { selectedItem: Subscriber -> listItemClicked(selectedItem) }
        })
    }

    //recycler view
    private fun initRecyclerView() {
        binding.subscriberRecyclerView.layoutManager = LinearLayoutManager(this)
        displaySubscriberList()
    }

    //handle on click event on the recycler's list item
    private fun  listItemClicked(subscriber: Subscriber) {
        Toast.makeText(this,"Item clicked ${subscriber.name}",Toast.LENGTH_LONG).show()
        //call the viewmodel's method
        subscriberViewModel.initUpdateAndDelete(subscriber)
    }
}