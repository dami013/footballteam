package it.com.uninsubria.footballteam.adapter

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import it.com.uninsubria.footballteam.Atleta
import it.com.uninsubria.footballteam.PlayerDetails
import it.com.uninsubria.footballteam.R

class PlayerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val nome = view.findViewById<TextView>(R.id.tvPlayerName)
    val cognome = view.findViewById<TextView>(R.id.tvSurname)
    val cf = view.findViewById<TextView>(R.id.tvCf)
    val foto = view.findViewById<ImageView>(R.id.ivPlayer)



    fun render(atleta: Atleta) {

        nome.text = atleta.nome
        cognome.text = atleta.cognome
        cf.text = atleta.codiceFiscale


        Glide.with(foto.context)
            .load(atleta.immagine)
            .into(foto)

        // quando selezioni si aprono i dettagli dell'atleta
        itemView.setOnClickListener {
            val intent = Intent(itemView.context, PlayerDetails::class.java)
            // passiamo il codice fiscale
            intent.putExtra("cf",cf.text)
            Log.e("ATTENZIONE", "${cf}")
            itemView.context.startActivity(intent)
        }
    }


}