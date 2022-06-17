package it.com.uninsubria.footballteam

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
    // Serve un costruttore vuoto per firebase

    constructor() : this("","","","","","","","","")

    // ci interssa di porre nome e cognome
    override fun toString(): String {
        return nome.toString()
        return cognome.toString()
        return ruolo.toString()
    }
    // Permette di sovrascrivere i valori precedente con i nuovi
    fun set(atleta: Atleta) {
        nome = atleta.nome
        cognome = atleta.cognome
        dataNascita = atleta.dataNascita
        ruolo = atleta.ruolo
        phone = atleta.phone
        certificazione = atleta.certificazione
        image = atleta.image
        risultati = atleta.risultati
        id = atleta.id

    }

}
