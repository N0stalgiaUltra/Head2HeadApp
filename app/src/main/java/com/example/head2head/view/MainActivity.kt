package com.example.head2head.view

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.lifecycle.map
import com.bumptech.glide.Glide
import com.example.head2head.R
import com.example.head2head.data.local.model.TeamLocal
import com.example.head2head.databinding.ActivityMainBinding
import com.example.head2head.view.dropdown.CustomDropdownItem
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), ImageLoader {

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

                binding.teamSpinner1.adapter = setAdapter(items)
                binding.teamSpinner2.adapter = setAdapter(items)
                /* TODO: Trabalhar toda essa situação entre os backgrounds dos itens do dropdown e do adapter*/
                binding.teamSpinner1.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener{
                        override fun onItemSelected(
                            parent: AdapterView<*>?,
                            view: View?,
                            position: Int,
                            id: Long
                        ) {
                            //chamado no clique do objeto dentro do Dropdown
                            //view?.setBackgroundColor(applicationContext.getColor(R.color.transparent))
                        }

                        override fun onNothingSelected(p0: AdapterView<*>?) {
                        }

                    }

            }
        })

        getTeams()
    }

    override fun onDestroy() {
        super.onDestroy()
        clearCache(this)
    }

    private fun setAdapter(items: List<TeamLocal>) : CustomDropdownItem{
        val adapter = CustomDropdownItem(
            this)
        adapter.setList(items)
        return adapter
    }

    private fun getTeams(){
        mainViewModel.getTeamsLocal()
    }
}
