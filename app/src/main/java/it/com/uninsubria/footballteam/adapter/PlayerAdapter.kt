package it.com.uninsubria.footballteam.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import it.com.uninsubria.footballteam.Atleta
import it.com.uninsubria.footballteam.R

class PlayerAdapter(private val giocatori:List<Atleta>) : RecyclerView.Adapter<PlayerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PlayerViewHolder(layoutInflater.inflate(R.layout.giocatore, parent,false))
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        val oggetto = giocatori[position]
        holder.render(oggetto)

    }

    override fun getItemCount(): Int = giocatori.size


}