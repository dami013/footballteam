package it.com.uninsubria.footballteam.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import it.com.uninsubria.footballteam.Atleta
import it.com.uninsubria.footballteam.R
import kotlinx.android.synthetic.main.giocatore.view.*

class PlayerAdapter(private val atleti:List<Atleta>) : RecyclerView.Adapter<PlayerViewHolder>() {
    private var isSelected = false
    private val selectedList = mutableListOf<Int>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context).inflate(R.layout.giocatore, parent,false)
        return PlayerViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        val oggetto = atleti[position]
        holder.itemView.checkPlayer.visibility = View.GONE
        holder.render(oggetto)

        holder.itemView.setOnLongClickListener {
            selectPlayer(holder,position)
            true
        }
    }

    private fun selectPlayer(holder: PlayerViewHolder,position: Int) {
        isSelected = true
        var counter = 0
        if(position in selectedList) {
            Toast.makeText(holder.itemView.context, "Player già selezionato", Toast.LENGTH_SHORT)
                .show()
            counter++
            Log.d("Count", counter.toString())

        } else {
            Toast.makeText(holder.itemView.context, "Player aggiunto ai selezionati", Toast.LENGTH_SHORT)
                .show()
            selectedList.add(position)
        }
        if(counter > 1) {
            selectedList.removeAt(position)
            holder.itemView.checkPlayer.visibility = View.GONE

        }
        holder.itemView.checkPlayer.visibility = View.VISIBLE
        Log.d("Player", selectedList.toString())


    }

    override fun getItemCount(): Int = atleti.size




}