package it.com.uninsubria.footballteam

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class Atleta(
    var nome: String?,
    var cognome: String?,
    var dataNascita: String?,
    var codiceFiscale: String?,
    var ruolo: String?,
    var telefono: String?,
    var certificazioni: String?,
    var immagine : String?,
    var risultati: String?,
) : Serializable, Parcelable {
    // Serve un constructor vuoto per firebase

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    constructor() : this("","","","","","","","","")

    // ci interssa di porre nome e cognome
    override fun toString(): String {
        return nome.toString() + cognome.toString() + ruolo.toString()
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

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nome)
        parcel.writeString(cognome)
        parcel.writeString(dataNascita)
        parcel.writeString(codiceFiscale)
        parcel.writeString(ruolo)
        parcel.writeString(telefono)
        parcel.writeString(certificazioni)
        parcel.writeString(immagine)
        parcel.writeString(risultati)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Atleta> {
        override fun createFromParcel(parcel: Parcel): Atleta {
            return Atleta(parcel)
        }

        override fun newArray(size: Int): Array<Atleta?> {
            return arrayOfNulls(size)
        }
    }
}