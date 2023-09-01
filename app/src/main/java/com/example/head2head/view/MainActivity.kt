package com.example.head2head.view

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.map
import com.example.head2head.R
import com.example.head2head.data.local.model.TeamLocal
import com.example.head2head.databinding.ActivityMainBinding
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
        /*TODO: Adicionar Observable para o viewModel */

        mainViewModel.teamList.observe(this, Observer {
            items ->
            if(items != null){
                val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, items.map { it.name })
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.teamSpinner1.adapter = adapter

            }
        })
        getTeams()
    }

    private fun getTeams(){
        mainViewModel.getTeamsLocal()
    }

}