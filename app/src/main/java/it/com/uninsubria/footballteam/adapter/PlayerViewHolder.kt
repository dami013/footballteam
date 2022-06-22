package it.com.uninsubria.footballteam.adapter

import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import it.com.uninsubria.footballteam.Atleta
import it.com.uninsubria.footballteam.PlayerDetails
import it.com.uninsubria.footballteam.R

class PlayerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val nome = view.findViewById<TextView>(R.id.tvPlayerName)
    val cognome = view.findViewById<TextView>(R.id.tvSurname)
    val ruolo = view.findViewById<TextView>(R.id.tvRole)
    val foto = view.findViewById<ImageView>(R.id.ivPlayer)


    fun render(atleta: Atleta) {
        nome.text = atleta.nome
        cognome.text = atleta.cognome
        ruolo.text = atleta.ruolo

        val requestOption = RequestOptions()
            .placeholder(R.drawable.icona)
            .error(R.drawable.icona) // In caso di errore
        Glide.with(foto.context)
            .applyDefaultRequestOptions(requestOption)
            .load(atleta.photo)
            .into(foto)

        itemView.setOnClickListener {
            var a = Intent(itemView.context, PlayerDetails::class.java)
            itemView.context.startActivity(a)

        }


    }
}