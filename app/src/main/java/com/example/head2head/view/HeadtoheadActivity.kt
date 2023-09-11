package com.example.head2head.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.head2head.databinding.ActivityHeadtoheadBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HeadtoheadActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHeadtoheadBinding
    private val h2HViewModel: H2HViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHeadtoheadBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onStart() {
        super.onStart()

        h2HViewModel.teamsH2HList.observe(this){
            teams -> teams.map {
                /*TODO: Organizar o Adapter*/
            }
        }
    }

    override fun onResume() {
        super.onResume()

        CoroutineScope(Dispatchers.IO).launch {

            if(h2HViewModel.teamsH2HList.value != null) {
                val test = h2HViewModel.getWinnersCount(120)
                Log.d("H2H", test)
            }
            else{
                getH2HData()
            }

        }


    }

    private suspend fun getH2HData(){
        //Botafogo e Internacional
        h2HViewModel.getH2HRemote(120, 119)
    }

}