package com.n0stalgiaultra.head2head.view.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import com.n0stalgiaultra.head2head.databinding.ActivityMainBinding
import com.n0stalgiaultra.head2head.domain.mapper.team.TeamItem
import com.n0stalgiaultra.head2head.view.dropdown.CustomDropdownItem
import com.n0stalgiaultra.head2head.view.util.ImageLoader
import com.n0stalgiaultra.head2head.view.viewmodels.TeamViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.reflect.KMutableProperty0

class MainActivity : AppCompatActivity(), ImageLoader {
    private val mainViewModel: TeamViewModel by viewModel()
    private lateinit var  binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences
    private var id1: Long = 0
    private var id2: Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = getSharedPreferences("theme", Context.MODE_PRIVATE)

    }

    override fun onStart() {
        super.onStart()
        binding.themeSwitch.isChecked = sharedPreferences.getBoolean("theme_state", false)
        setTheme(binding.themeSwitch.isChecked)
        binding.buttonH2H.setOnClickListener {
            val result = compareId()

            if(!result){
                val intent = Intent(applicationContext, HeadtoheadActivity::class.java)
                intent.putExtra("teamId", id1.toInt())
                intent.putExtra("teamId2", id2.toInt())
                startActivity(intent)
            }
            else{
                Toast.makeText(this,
                    "You must choose different teams",
                    Toast.LENGTH_SHORT).show()
            }
        }

        binding.themeSwitch.setOnCheckedChangeListener {
               _, checked ->
                setTheme(checked)
                saveData(checked)
        }
    }
    override fun onResume() {
        super.onResume()
        setMemory(this, onLowMemory = false)

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

    override fun onPause() {
        super.onPause()
        setMemory(this, onLowMemory = true)
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

    private fun saveData(theme: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean("theme_state", theme)
        editor.apply()
    }
    private fun setTheme(dark: Boolean){
        if(dark){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        else
        {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}
