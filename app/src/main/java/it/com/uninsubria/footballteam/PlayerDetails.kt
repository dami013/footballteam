package it.com.uninsubria.footballteam


import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_player_details.*

class PlayerDetails : AppCompatActivity() {
    private  lateinit var db: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_details)
        val cf = intent.getStringExtra("cf")
        insertData(cf!!)
    }

    private fun insertData(cf: String){
        val auth = Firebase.auth
        val currentUser = auth.currentUser
        val uid = currentUser!!.uid
        db = FirebaseDatabase.getInstance("https://footballteam-d5795-default-rtdb.firebaseio.com/")
            .getReference("Users")
            .child(uid)
            .child("Atleti")
            .child(cf)
        db.addValueEventListener(object : ValueEventListener {
            @SuppressLint("SetTextI18n")
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()) {
                    val atleta = snapshot.getValue(Atleta::class.java)
                    //manca foto
                    name.setText("nome ${atleta!!.nome}")
                    surname.setText("cognome ${atleta.cognome}")
                    codFisc.setText("codice fiscale : ${atleta.codiceFiscale}")
                    dataN.setText(" data di nascita  + ${atleta.dataNascita}")
                    ruolo.setText("ruolo + ${atleta.ruolo}")
                    phone.setText("telefono ${atleta.phone}")
                    results.setText("risultati ${atleta.risultati}")
                    certificazioni.setText("certificazioni ${atleta.certificazione}")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("TEST","Failed to read value")
            }
        })
    }
}