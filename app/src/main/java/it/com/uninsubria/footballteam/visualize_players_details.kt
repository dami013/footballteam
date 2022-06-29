package it.com.uninsubria.footballteam

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_visualize_players_details.*


class visualize_players_details : Fragment() {
    private  lateinit var db: DatabaseReference
    private var cf: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            cf = it.getString("cf")
            }
        insertData(cf!!)
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_visualize_players_details,container, false)
        return view
    }

    private fun insertData(cf: String){
        val auth = Firebase.auth
        val currentUser = auth.currentUser
        val uid = currentUser!!.uid
        var atleta :Atleta? = null
        db = FirebaseDatabase.getInstance("https://footballteam-d5795-default-rtdb.firebaseio.com/")
            .getReference("Users")
            .child(uid)
            .child("Atleti")
            .child(cf)
        db.addValueEventListener(object : ValueEventListener {
            @SuppressLint("SetTextI18n")
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()) {
                     atleta = snapshot.getValue(Atleta::class.java)
                    //manca foto
                    nome.text = "nome: ${atleta!!.nome}"
                    cognome.text = "cognome: ${atleta!!.cognome}"
                    codFisc.text = "codice fiscale: ${atleta!!.codiceFiscale}"
                    dataN.text = " data di nascita: ${atleta!!.dataNascita}"
                    ruolo.text = "ruolo: ${atleta!!.ruolo}"
                    telefono.text = "telefono: ${atleta!!.phone}"
                    risultati.text = "risultati: ${atleta!!.risultati}"
                    certificati.text = "certificazioni: ${atleta!!.certificazione}"
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("TEST","Failed to read value")
            }
        })
    }
}