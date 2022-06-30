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
    val cf = view.findViewById<TextView>(R.id.tvCf)
    val foto = view.findViewById<ImageView>(R.id.ivPlayer)
    val ruolo = view.findViewById<TextView>(R.id.tvRole)
  

    fun render(atleta: Atleta) {
        nome.text = atleta.nome
        cognome.text = atleta.cognome
        cf.text = atleta.codiceFiscale
        ruolo.text = atleta.ruolo
       // Log.w("Prova","${atleta.immagine}")
       // Log.w("Prova","${atleta.nome}")

        Glide.with(foto.context)
            .load(atleta.immagine)
            .into(foto)

        itemView.setOnClickListener {
            val intent = Intent(itemView.context, PlayerDetails::class.java)
            intent.putExtra("cf",cf.text)
            println("ATTENZIONE ${cf}")
            itemView.context.startActivity(intent)
        }
    }
}