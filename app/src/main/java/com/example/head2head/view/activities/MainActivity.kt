package com.example.head2head.view.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.head2head.databinding.ActivityMainBinding
import com.example.head2head.domain.mapper.team.TeamItem
import com.example.head2head.view.dropdown.CustomDropdownItem
import com.example.head2head.view.util.ImageLoader
import com.example.head2head.view.viewmodels.TeamViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.reflect.KMutableProperty0

class MainActivity : AppCompatActivity(), ImageLoader {

    private val mainViewModel: TeamViewModel by viewModel()
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
                /*TODO: CHAMAR ACTIVITY DO H2H, PASSANDO DADOS PELO Intent*/
                //Toast.makeText(this, "IDs Accepted, call new activity", Toast.LENGTH_SHORT).show()
                val intent = Intent(applicationContext, HeadtoheadActivity::class.java)
                intent.putExtra("teamId", id1.toInt())
                intent.putExtra("teamId2", id2.toInt())
                startActivity(intent)

            }
            else{
                Toast.makeText(this, "You must choose different teams", Toast.LENGTH_SHORT).show()
            }
        }
    }
    override fun onResume() {
        super.onResume()
        mainViewModel.teamItemList.observe(this, Observer {
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

    private fun setAdapter(items: List<TeamItem>) : CustomDropdownItem{
        val adapter = CustomDropdownItem(
            this)
        adapter.setList(items)
        return adapter
    }


    /**
     *  Method used to get the ID from the clicked item inside the CustomDropdown
     *  @param spinner: the current spinner
     *  @param idProperty: KMutableProperty used to store the ID
     */
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

    private fun getTeams(){
        mainViewModel.getTeamsLocal()
    }

    private fun compareId(): Boolean{
        return id1 == id2
    }

}
