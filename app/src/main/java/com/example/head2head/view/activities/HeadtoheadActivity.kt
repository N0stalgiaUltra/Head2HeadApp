package com.example.head2head.view.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.head2head.databinding.ActivityHeadtoheadBinding
import com.example.head2head.domain.mapper.team.TeamCard
import com.example.head2head.view.util.ImageLoader
import com.example.head2head.view.viewmodels.H2HViewModel
import com.example.head2head.view.viewmodels.TeamViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HeadtoheadActivity : AppCompatActivity(), ImageLoader {
    private lateinit var binding: ActivityHeadtoheadBinding
    private val h2HViewModel: H2HViewModel by viewModel()
    private val teamViewModel: TeamViewModel by viewModel()
    var id1: Int = 0
    var id2: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHeadtoheadBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onStart() {
        super.onStart()



        h2HViewModel.teamsH2HList.observe(this){
            teams -> teams.map {
                /*TODO: Organizar o ViewBinding*/

            }
        }
    }

    override fun onResume() {
        super.onResume()

        Log.d("Teams", "${teamViewModel.teamCardList.value?.size}")
        //setTexts(team1, team2)
        teamViewModel.teamCardList.observe(this){
            items ->
            if(items != null){
                id1 = intent.getIntExtra("teamId", -1)
                id2 = intent.getIntExtra("teamId2", -1)

                val team1 = teamViewModel.getTeamCard(id1)
                val team2 = teamViewModel.getTeamCard(id2)
                setTexts(team1, team2)
            }
        }

        CoroutineScope(Dispatchers.IO).launch {

            if(h2HViewModel.teamsH2HList.value != null) {
                val test = h2HViewModel.getWinnersCount(id1)
                Log.d("H2H", test)
            }
            else{
                getH2HData(id1, id2)
            }

        }

        teamViewModel.getTeamsLocal()

    }

    private suspend fun getH2HData(id1: Int, id2: Int){
        h2HViewModel.getH2HRemote(id1, id2)
    }

    private fun setTexts(team1: TeamCard?, team2: TeamCard?){

        binding.hthHeader.hthMainText.text = team1?.teamAbvr
        binding.hthHeader.hthMainText2.text = team2?.teamAbvr

        getImage(this, binding.hthHeader.hthMainImg, team1!!.teamImage)
        getImage(this, binding.hthHeader.hthMainImg2, team2!!.teamImage)

    }

}