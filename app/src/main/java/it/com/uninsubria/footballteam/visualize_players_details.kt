package it.com.uninsubria.footballteam

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import it.com.uninsubria.footballteam.fragments.Change_player_details
import kotlinx.android.synthetic.main.activity_welcome.*
import kotlinx.android.synthetic.main.fragment_visualize_players_details.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class visualize_players_details : Fragment() {
    private  lateinit var db: DatabaseReference
    private var param1: String? = null
    private var param2: String? = null
    private var cf :String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            val bundle = arguments
            if (bundle != null) {
                cf = bundle.getString("cf").toString()
            }
            insertData(cf)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val changePlayers = Change_player_details()
        mod.setOnClickListener(View.OnClickListener {
            val fragmentManager = parentFragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(
                R.id.fragmentContainer,
                changePlayers
            )
            fragmentTransaction.addToBackStack(null)
            val args = Bundle()
            args.putString("cf", cf)
            changePlayers.arguments = args
            fragmentTransaction.commit()
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_visualize_players_details,container, false)
        insertData(cf)
        return view
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
                    nome.text = "nome: ${atleta!!.nome}"
                    cognome.text = "cognome: ${atleta.cognome}"
                    codFisc.text = "codice fiscale: ${atleta.codiceFiscale}"
                    dataN.text = " data di nascita: ${atleta.dataNascita}"
                    ruolo.text = "ruolo: ${atleta.ruolo}"
                    telefono.text = "telefono: ${atleta.phone}"
                    risultati.text = "risultati: ${atleta.risultati}"
                    certificati.text = "certificazioni: ${atleta.certificazione}"
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("TEST","Failed to read value")
            }
        })
    }
}