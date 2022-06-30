package it.com.uninsubria.footballteam

import java.io.Serializable

data class Atleta(
    var nome: String,
    var cognome: String,
    var dataNascita: String,
    var codiceFiscale: String,
    var ruolo: String,
    var phone: String,
    var certificazione: String,
    var photo : String,
    var risultati: String,
    ) : Serializable {
    // Serve un costruttore vuoto per firebase
    
    constructor() : this("","","","","","","","","")

    // ci interssa di porre nome e cognome
    override fun toString(): String {
        var str : String = nome.toString()
        str+= cognome
        str+= ruolo
        return str
    }
    // Permette di sovrascrivere i valori precedente con i nuovi
    fun set(atleta: Atleta) {
        nome = atleta.nome
        cognome = atleta.cognome
        dataNascita = atleta.dataNascita
        codiceFiscale = atleta.codiceFiscale
        ruolo = atleta.ruolo
        telefono = atleta.telefono
        certificazioni = atleta.certificazioni
        immagine = atleta.immagine
        risultati = atleta.risultati

    }
}
