package it.com.uninsubria.footballteam.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.auth.User
import it.com.uninsubria.footballteam.Atleta
import it.com.uninsubria.footballteam.R
import kotlinx.android.synthetic.main.giocatore.view.*


class PlayerAdapter(private val atleti:List<Atleta>) : RecyclerView.Adapter<PlayerViewHolder>() {
    private var isSelected = false
    private var selectedPlayers = arrayListOf<Atleta>()
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
            selectPlayer(holder,position)
            true
        }
    }

    private fun selectPlayer(holder: PlayerViewHolder,position: Int) {

        if(atleti[position] in selectedPlayers) {
            Toast.makeText(holder.itemView.context, "Player già selezionato", Toast.LENGTH_SHORT)
                .show()
            isSelected = false

        } else {
            Toast.makeText(holder.itemView.context, "Player aggiunto ai selezionati", Toast.LENGTH_SHORT)
                .show()
            isSelected = true
            holder.itemView.checkPlayer.visibility = View.VISIBLE
           // selectedList.add(position)
            //val player = atleti[position]
            selectedPlayers.add(atleti[position])

        }
        if(!isSelected) {
            //val player = atleti[position]
            //selectedList.removeAt(position)
            selectedPlayers.remove(atleti[position])
            holder.itemView.checkPlayer.visibility = View.GONE
        }

        Log.d("Player", selectedPlayers.toString())


    }

    override fun getItemCount(): Int = atleti.size


    fun getSelected(): ArrayList<Atleta> {
        return selectedPlayers
    }




}