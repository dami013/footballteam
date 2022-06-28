package it.com.uninsubria.footballteam

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
    ) {
    // Serve un costruttore vuoto per firebase

    constructor() : this("","","","","","","","","")

    // ci interssa di porre nome e cognome
    override fun toString(): String {
        return nome.toString()
        return cognome
        return ruolo
    }
    // Permette di sovrascrivere i valori precedente con i nuovi
}
