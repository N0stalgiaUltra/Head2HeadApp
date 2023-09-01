package com.example.head2head.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.head2head.R
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity2 : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            getTeams()
        }
    }
    private suspend fun getTeams(){
        mainViewModel.getAllTeams()
    }
}