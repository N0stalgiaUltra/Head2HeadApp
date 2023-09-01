package com.example.head2head.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.map
import com.example.head2head.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val mainViewModel: MainViewModel by viewModel()

    override fun onResume() {
        super.onResume()
        /*TODO: Adicionar Observable para o viewModel */
        getTeams()
    }

    private fun getTeams(){
        mainViewModel.getTeamsLocal()
    }
}