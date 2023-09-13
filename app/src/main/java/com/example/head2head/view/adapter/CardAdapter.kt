package com.example.head2head.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.head2head.data.remote.dto.h2h.H2HDto
import com.example.head2head.databinding.HthCardItemBinding
import com.example.head2head.domain.mapper.team.TeamCard

class CardAdapter: RecyclerView.Adapter<CardViewHolder>() {

    private var _items = emptyList<H2HDto?>()
    private var _teams = emptyList<TeamCard?>()

    fun clearItems(){
        _items = emptyList()
        _teams = emptyList()
        Log.d("RecView", "Limpei as listas")

    }

    fun setItems(items: List<H2HDto?>, teams: List<TeamCard?>){
        _items = items
        _teams = teams
        Log.d("RecView", "setei os itens")

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = HthCardItemBinding.inflate(inflater, parent, false)
        Log.d("RecView", "Criei o VH")

        return CardViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return _items.size

    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val item = _items[position]!!
        holder.bindData(item, _teams)
        Log.d("RecView", "fazendo o bind dos dados")

    }

}