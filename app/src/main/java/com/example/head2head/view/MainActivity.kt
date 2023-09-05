package com.example.head2head.view

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.map
import com.bumptech.glide.Glide
import com.example.head2head.R
import com.example.head2head.data.local.model.TeamLocal
import com.example.head2head.databinding.ActivityMainBinding
import com.example.head2head.view.dropdown.CustomDropdownItem
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModel()
    private lateinit var  binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
    override fun onResume() {
        super.onResume()

        mainViewModel.teamList.observe(this, Observer {
            items ->
            if(items != null){
                val adapter = CustomDropdownItem(
                    this)
                adapter.setList(items)
                binding.teamSpinner1.adapter = adapter


            }
        })

        getTeams()
    }

    private fun getTeams(){
        mainViewModel.getTeamsLocal()
    }

    override fun onDestroy() {
        super.onDestroy()
        Glide.get(this).clearDiskCache()
        Glide.get(this).clearMemory()
    }
}