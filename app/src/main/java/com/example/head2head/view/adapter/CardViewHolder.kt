package com.example.head2head.view.adapter

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.example.head2head.data.remote.dto.h2h.H2HDto
import com.example.head2head.databinding.HthCardItemBinding
import com.example.head2head.domain.mapper.team.TeamCard
import com.example.head2head.view.util.ImageLoader

class CardViewHolder(
    private val cardViewBinding: HthCardItemBinding
):RecyclerView.ViewHolder(cardViewBinding.root), ImageLoader {

    fun bindData(data: H2HDto, teams: List<TeamCard?>){

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

        Log.d("RecView", "Fiz o binding")


        //insert the score data
    }
}