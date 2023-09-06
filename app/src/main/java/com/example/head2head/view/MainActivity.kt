package com.example.head2head.view

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
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
import kotlin.reflect.KMutableProperty0

class MainActivity : AppCompatActivity(), ImageLoader {

    private val mainViewModel: MainViewModel by viewModel()
    private lateinit var  binding: ActivityMainBinding
    private var id1: Long = 0
    private var id2: Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        binding.buttonH2H.setOnClickListener {
            val result = compareId()

            if(!result){
                Toast.makeText(this, "IDs Accepted, call new activity", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this, "You must choose different teams", Toast.LENGTH_SHORT).show()
            }
        }
    }
    override fun onResume() {
        super.onResume()
        mainViewModel.teamList.observe(this, Observer {
            items ->
            if(items != null){
                binding.teamSpinner1.adapter = setAdapter(items)
                binding.teamSpinner2.adapter = setAdapter(items)

                setOnItemSelectedListener(binding.teamSpinner1, ::id1)
                setOnItemSelectedListener(binding.teamSpinner2, ::id2)

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

    private fun compareId(): Boolean{
        return id1 == id2
    }

    private fun setOnItemSelectedListener(
        spinner: Spinner,
        idProperty: KMutableProperty0<Long>
    ) {
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val adapter = spinner.adapter as CustomDropdownItem
                idProperty.set(adapter.getItemId(position))
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }
}
