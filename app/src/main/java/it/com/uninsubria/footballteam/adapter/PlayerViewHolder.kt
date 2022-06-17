package it.com.uninsubria.footballteam.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import it.com.uninsubria.footballteam.Atleta
import it.com.uninsubria.footballteam.R

class PlayerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val nome = view.findViewById<TextView>(R.id.tvPlayerName)
    val dataNascita = view.findViewById<TextView>(R.id.tvBirthDate)
    val ruolo = view.findViewById<TextView>(R.id.tvRole)
    val telefono = view.findViewById<TextView>(R.id.tvPhone)
    val certificazione = view.findViewById<TextView>(R.id.tvCertification)
    val result = view.findViewById<TextView>(R.id.tvResults)
    val matricola = view.findViewById<TextView>(R.id.tvIdNumber)
    val foto = view.findViewById<ImageView>(R.id.ivPlayer)

    fun render(atleta: Atleta) {
        nome.text = atleta.nome
        dataNascita.text = atleta.dataNascita
        ruolo.text = atleta.ruolo
        telefono.text = atleta.phone
        certificazione.text = atleta.certificazione
        result.text = atleta.risultati
        matricola.text = atleta.id
        val requestOption = RequestOptions()
            .placeholder(R.drawable.icona)
            .error(R.drawable.icona) // In caso di errore
        Glide.with(foto.context)
            .applyDefaultRequestOptions(requestOption)
            .load(atleta.photo)
            .into(foto)

    }
}