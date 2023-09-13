package com.example.head2head.view.adapter

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.head2head.data.remote.dto.h2h.H2HDto
import com.example.head2head.databinding.HthCardItemBinding
import com.example.head2head.domain.mapper.team.TeamCard
import com.example.head2head.view.util.DateFormatter
import com.example.head2head.view.util.ImageLoader

class CardViewHolder(
    private val cardViewBinding: HthCardItemBinding
):RecyclerView.ViewHolder(cardViewBinding.root), ImageLoader, DateFormatter {

    @RequiresApi(Build.VERSION_CODES.O)
    fun bindData(data: H2HDto, teams: List<TeamCard?>){
        /*TODO: Organizar times case/visitante*/
        //first team
        cardViewBinding.hthCardTeam.text = teams[0]?.teamAbvr
        getImage(cardViewBinding.root.context,
            cardViewBinding.hthCardLogo,
            teams[0]?.teamImage!!
            )
        //second team
        cardViewBinding.hthCardTeam2.text = teams[1]?.teamAbvr
        getImage(cardViewBinding.root.context,
            cardViewBinding.hthCardLogo2,
            teams[1]?.teamImage!!
        )

        cardViewBinding.hthCardDate.text = getFormatDate(data.fixture.date)
        cardViewBinding.hthCardStadium.text = data.fixture.venue.name

        //insert the score data
        setScores(data, teams)
    }

    private fun setScores(data: H2HDto, teams: List<TeamCard?>){
        //Verificar se o primeiro time é o home ou away
        //recuperar valor do score
        //colocar na view

        /*
        * 3x2 -> Bot 3 x 2 FLU
        * 1x1 ->
        * 0x0
        * 0x1 ->
        * 0x2
        *
        * */


        if(teams[0]?.teamId == data.teams.home.id){
            //time é o mandante.
            cardViewBinding.hthCardScore.hthScoreHome.text = data.goals.home.toString()
            cardViewBinding.hthCardScore.hthScoreAway.text = data.goals.away.toString()
        }
        else{
            cardViewBinding.hthCardScore.hthScoreHome.text = data.goals.away.toString()
            cardViewBinding.hthCardScore.hthScoreAway.text = data.goals.home.toString()
        }
    }
}