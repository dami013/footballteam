package it.com.uninsubria.footballteam.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
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
    val foto = view.findViewById<ImageView>(R.id.imageView)

    fun render(atleta: Atleta) {
        nome.text = atleta.nome
        dataNascita.text = atleta.dataNascita
        ruolo.text = atleta.ruolo
        telefono.text = atleta.phone
        certificazione.text = atleta.certificazione
        result.text = atleta.risultati
        matricola.text = atleta.id


    }
}