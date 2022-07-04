package it.com.uninsubria.footballteam.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import it.com.uninsubria.footballteam.Atleta
import it.com.uninsubria.footballteam.R
import kotlinx.android.synthetic.main.giocatore.view.*


class PlayerAdapter(private val atleti:List<Atleta>, val itemClick: (Int) -> Unit) : RecyclerView.Adapter<PlayerViewHolder>() {
    private var isSelected = false
     var selectedPlayers = arrayListOf<Atleta>()
    //private val selectedList = arrayListOf<Int>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context).inflate(R.layout.giocatore, parent,false)
        return PlayerViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        val oggetto = atleti[position]
        holder.itemView.checkPlayer.visibility = View.GONE
        holder.render(oggetto)

        holder.itemView.setOnLongClickListener {
            itemClick(position)
            holder.itemView.checkPlayer.visibility = View.VISIBLE
            true
        }
    }

    override fun getItemCount(): Int = atleti.size






}