package com.n0stalgiaultra.head2head.view.dropdown

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.n0stalgiaultra.head2head.R
import com.n0stalgiaultra.head2head.domain.mapper.team.TeamItem
import com.n0stalgiaultra.head2head.view.util.ImageLoader


class CustomDropdownItem(
    private val context: Context,
): BaseAdapter(), ImageLoader {
    private var teamsList: List<TeamItem> = emptyList()
    /*TODO: DiffUtil*/
    fun setList(teams: List<TeamItem>){
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

    override fun getItem(position: Int): TeamItem {
        return teamsList[position]
    }

    override fun getItemId(position: Int): Long {
        return teamsList[position].teamId.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val viewHolder : ViewHolder
        var itemView = convertView

        viewHolder = if (itemView == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)
                    as LayoutInflater

            itemView = inflater.inflate(R.layout.card_item, parent, false)
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
        val teamItem = getItem(position)
        viewHolder.teamName?.text = teamItem.teamName

        getImage(
            context,
            viewHolder.teamIcon,
            teamItem.teamImage)

    }

}