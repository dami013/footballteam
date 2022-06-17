package it.com.uninsubria.footballteam

import java.util.*

data class Atleta(
    var nome: String?,
    var cognome: String?,
    var dataNascita: String?,
    var ruolo: String?,
    var phone: String?,
    var certificazione: String?,
    var image : String?,
    var risultati: String?,
    var id: String?) {
    // Serve un costruttore vuoto per fb

    constructor() : this("","","","","","","","","")

    // ci interssa di porre unicamente la descrizione
    override fun toString(): String {
        return nome.toString()
        return cognome.toString()
        return dataNascita.toString()
        return ruolo.toString()
        return phone.toString()
        return certificazione.toString()
        return image.toString()
        return risultati.toString()
        return id.toString()
    }
    // Permette di sovrascrivere i valori precedente con i nuovi
    fun set(item: Atleta) {
        nome = item.nome
        cognome = item.cognome
        dataNascita = item.dataNascita
        ruolo = item.ruolo
        phone = item.phone
        certificazione = item.certificazione
        image = item.image
        risultati = item.risultati
        id = item.id

    }

}
