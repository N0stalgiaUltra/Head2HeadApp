package com.n0stalgiaultra.head2head.view.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.n0stalgiaultra.head2head.databinding.ActivityHeadtoheadBinding
import com.n0stalgiaultra.head2head.domain.mapper.team.TeamCard
import com.n0stalgiaultra.head2head.view.adapter.CardAdapter
import com.n0stalgiaultra.head2head.view.util.ImageLoader
import com.n0stalgiaultra.head2head.view.viewmodels.H2HViewModel
import com.n0stalgiaultra.head2head.view.viewmodels.TeamViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HeadtoheadActivity : AppCompatActivity(), ImageLoader {
    private lateinit var binding: ActivityHeadtoheadBinding
    private val h2HViewModel: H2HViewModel by viewModel()
    private val teamViewModel: TeamViewModel by viewModel()
    private val cardAdapter = CardAdapter()

    var id1: Int = 0
    var id2: Int = 0
    private var teamCards: List<TeamCard?> = emptyList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHeadtoheadBinding.inflate(layoutInflater)
        setContentView(binding.root)
        id1 = intent.getIntExtra("teamId", -1)
        id2 = intent.getIntExtra("teamId2", -1)

        if(teamViewModel.teamList.value == null)
            teamViewModel.getTeamsLocal()
    }

    override fun onStart() {
        super.onStart()


        h2HViewModel.teamsH2HList.observe(this){
            teams ->
                if(teams.isNotEmpty() && teamCards.isNotEmpty()) {
                    cardAdapter.clearItems()
                    cardAdapter.setItems(teams, teamCards)
                    setupRecyclerView()

                    CoroutineScope(Dispatchers.IO).launch {
                        val count = h2HViewModel.getWinnersCount(id1)

                        binding.hthHeader.homeWinCount.text = count.first.toString()
                        binding.hthHeader.awayWinCount.text = count.second.toString()
                        binding.hthHeader.tiesCount.text = "Ties: ${count.third}"
                    }
                }
        }
    }

    override fun onResume() {
        super.onResume()
        setMemory(this, onLowMemory = false)
        teamViewModel.teamCardList.observe(this){
            items ->
                if(items != null){
                    val team1 = teamViewModel.getTeamCard(id1)
                    val team2 = teamViewModel.getTeamCard(id2)
                    teamCards = listOf(team1, team2)
                    setTexts(team1, team2)
            }
            else
            {
                Log.d("Items", "Items é null")
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            if(h2HViewModel.teamsH2HList.value == null){
                getH2HData(id1, id2)
            }

        }



    }

    override fun onDestroy() {
        super.onDestroy()
        clearCache(this)
    }

    private suspend fun getH2HData(id1: Int, id2: Int){
        h2HViewModel.getRemoteData(id1, id2)
    }

    private fun setTexts(team1: TeamCard?, team2: TeamCard?){

        binding.hthHeader.hthMainText.text = team1?.teamAbvr
        binding.hthHeader.hthMainText2.text = team2?.teamAbvr

        getImage(this, binding.hthHeader.hthMainImg, team1!!.teamImage)
        getImage(this, binding.hthHeader.hthMainImg2, team2!!.teamImage)

    }

    private fun setupRecyclerView(){
        binding.hthRecyclerView.apply {
            layoutManager = GridLayoutManager(applicationContext, 1)
            adapter = cardAdapter
        }
    }
}