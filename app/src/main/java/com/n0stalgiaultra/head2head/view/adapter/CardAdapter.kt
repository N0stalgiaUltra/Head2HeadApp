package com.n0stalgiaultra.head2head.view.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.n0stalgiaultra.head2head.data.remote.dto.h2h.H2HDto
import com.n0stalgiaultra.head2head.databinding.HthCardItemBinding
import com.n0stalgiaultra.head2head.domain.mapper.team.TeamCard

class CardAdapter: RecyclerView.Adapter<CardViewHolder>() {

    private var _items = emptyList<H2HDto?>()
    private var _teams = emptyList<TeamCard?>()

    fun clearItems(){
        _items = emptyList()
        _teams = emptyList()

    }

    fun setItems(items: List<H2HDto?>, teams: List<TeamCard?>){
        _items = items
        _teams = teams
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = HthCardItemBinding.inflate(inflater, parent, false)

        return CardViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return _items.size

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val item = _items[position]!!
        holder.bindData(item, _teams)

    }

}