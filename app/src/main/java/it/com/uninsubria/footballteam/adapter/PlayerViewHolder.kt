package it.com.uninsubria.footballteam.adapter

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import it.com.uninsubria.footballteam.Atleta
import it.com.uninsubria.footballteam.PlayerDetails
import it.com.uninsubria.footballteam.R
import kotlinx.android.synthetic.main.giocatore.view.*

class PlayerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val nome = view.findViewById<TextView>(R.id.tvPlayerName)
    val cognome = view.findViewById<TextView>(R.id.tvSurname)
    val ruolo = view.findViewById<TextView>(R.id.tvRole)
    val foto = view.findViewById<CircleImageView>(R.id.ivPlayer)


    fun render(atleta: Atleta) {
        nome.text = atleta.nome
        cognome.text = atleta.cognome
        ruolo.text = atleta.ruolo
       // Log.w("Prova","${atleta.immagine}")
       // Log.w("Prova","${atleta.nome}")

        Glide.with(foto.context)
            .load(atleta.immagine)
            .into(foto)

    }
}