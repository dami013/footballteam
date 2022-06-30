package it.com.uninsubria.footballteam.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import it.com.uninsubria.footballteam.Atleta
import it.com.uninsubria.footballteam.R

class PlayerAdapter(private val atleti:List<Atleta>) : RecyclerView.Adapter<PlayerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context).inflate(R.layout.giocatore, parent,false)
        return PlayerViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        val oggetto = atleti[position]
        holder.render(oggetto)
    }
    override fun getItemCount(): Int = atleti.size
}