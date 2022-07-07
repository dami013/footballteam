package it.com.uninsubria.footballteam.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import it.com.uninsubria.footballteam.Atleta
import it.com.uninsubria.footballteam.R
import kotlinx.android.synthetic.main.giocatore.view.*


class PlayerAdapter(private val atleti:List<Atleta>, val context : Context ,val itemClick: (Int) -> Unit) : RecyclerView.Adapter<PlayerViewHolder>() {

    private var selez = ArrayList<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context).inflate(R.layout.giocatore, parent,false)
        return PlayerViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        val oggetto = atleti[position]

        holder.itemView.checkPlayer.visibility = View.GONE

        val myResources = context.resources

        holder.render(oggetto)

        holder.itemView.setOnLongClickListener {
            itemClick(position)
            if(!selez.contains(holder.itemView.toString())){
                selez.add(holder.itemView.toString())
                holder.itemView.setBackgroundColor(myResources.getColor(R.color.light_yellow))
                Log.d("lista",selez.toString())
            }else{
                selez.remove(holder.itemView.toString())
                holder.itemView.setBackgroundColor(myResources.getColor(R.color.light_grey))
                Log.d("lista",selez.toString())
            }
            true
        }
    }

    override fun getItemCount(): Int = atleti.size
}