package it.com.uninsubria.footballteam.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import it.com.uninsubria.footballteam.Atleta
import it.com.uninsubria.footballteam.R
import it.com.uninsubria.footballteam.visualize_players_details
import kotlinx.android.synthetic.main.fragment_change_player_details.*
import kotlinx.android.synthetic.main.fragment_visualize_players_details.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class Change_player_details : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private  lateinit var db: DatabaseReference
    private var cf :String = ""
    private val visualizePlayers = visualize_players_details()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        val bundle = arguments
        if (bundle != null) {
            cf = bundle.getString("cf").toString()
            insertData(cf)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        visualizza.setOnClickListener(View.OnClickListener {
            val fragmentManager = parentFragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(
                R.id.fragmentContainer,
                visualizePlayers
            )
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_change_player_details, container, false)

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
                    println(atleta)
                    //manca foto
                    name.setText("nome: ${atleta!!.nome}".toUpperCase())
                    surname.setText("cognome: ${atleta.cognome}".toUpperCase())
                    codFiscale.setText("codice fiscale: ${atleta.codiceFiscale}".toUpperCase())
                    dataNasc.setText(" data di nascita: ${atleta.dataNascita}".toUpperCase())
                    ruoloc.setText("ruolo: ${atleta.ruolo}".toUpperCase())
                    phone.setText("telefono: ${atleta.phone}".toUpperCase())
                    results.setText("risultati: ${atleta.risultati}".toUpperCase())
                    certificazioni.setText("certificazioni: ${atleta.certificazione}".toUpperCase())
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("TEST","Failed to read value")
            }
        })
    }

}