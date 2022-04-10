package com.example.roomdemo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdemo.databinding.ListItemBinding
import com.example.roomdemo.db.Subscriber
import com.example.roomdemo.generated.callback.OnClickListener

class MyRecyclerViewAdapter(private val subscribers: List<Subscriber>,
                            private val clickListener: (Subscriber)-> Unit): RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        //we create the list item here
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding:ListItemBinding = DataBindingUtil.inflate(
                layoutInflater,
                R.layout.list_item,
                parent,
                false
            )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //We use  this to display the data on the list item
        holder.bind(subscribers[position],clickListener)
    }

    override fun getItemCount(): Int {
        return subscribers.size
    }
}

//creating the view holder as a separate class just to learn, as we've worked earlier with inner view holder classes in recycler adapter

//We use this class to bind values to each list item objects
class MyViewHolder(private val binding: ListItemBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(subscriber: Subscriber,clickListener: (Subscriber)-> Unit) {
        binding.nameTextView.text = subscriber.name
        binding.emailTextView.text = subscriber.email
        binding.listItemLayout.setOnClickListener {
            clickListener(subscriber)
        }
    }
}