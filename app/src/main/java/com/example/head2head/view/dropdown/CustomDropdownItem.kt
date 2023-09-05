package com.example.head2head.view.dropdown

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.head2head.R
import com.example.head2head.data.local.model.TeamLocal

class CustomDropdownItem(
    private val context: Context,
): BaseAdapter() {
    private var teamsList: List<TeamLocal> = emptyList()
    /*TODO: DiffUtil*/
    fun setList(teams: List<TeamLocal>){
        if(teamsList.isEmpty())
            this.teamsList = teams
        else
        {
            teamsList = emptyList()
            this.teamsList = teams
        }
    }

    private class ViewHolder{
        var teamIcon: ImageView? = null
        var teamName: TextView? = null
    }

    override fun getCount(): Int {
        return teamsList.size
    }

    override fun getItem(position: Int): TeamLocal {
        return teamsList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val viewHolder : ViewHolder
        var itemView = convertView

        viewHolder = if (itemView == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            itemView = inflater.inflate(R.layout.spinner_item, parent, false)
            val newViewHolder = ViewHolder()
            newViewHolder.teamIcon = itemView.findViewById(R.id.viewTeamLogo)
            newViewHolder.teamName = itemView.findViewById(R.id.viewTeamName)
            itemView.tag = newViewHolder
            newViewHolder
        } else {
            convertView?.tag as ViewHolder
        }

        bindData(viewHolder, position)
        return itemView!!
    }

    private fun bindData(viewHolder: ViewHolder, position: Int){
        Log.d("Adapter", "Entrei aqui")
        val teamItem = getItem(position)
        viewHolder.teamName?.text = teamItem.name

        /*TODO: Abstract into interface ? */
        Glide.with(context)
            .load(teamItem.logo)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.baseline_error_24)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(viewHolder.teamIcon!!)

    }

}