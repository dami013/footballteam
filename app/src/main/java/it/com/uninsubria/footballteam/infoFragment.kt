package it.com.uninsubria.footballteam

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_info.*
import kotlinx.android.synthetic.main.fragment_visualize_players_details.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class infoFragment : Fragment() {

    private  lateinit var db: DatabaseReference
    private var auth = Firebase.auth
    private lateinit var username: TextView
    private lateinit var email: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragmen
        val view =  inflater.inflate(R.layout.fragment_info, container, false)
        username = view.findViewById(R.id.username)
        email = view.findViewById(R.id.email)
        return view
    }

    // Inserimento dati
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        insertData()
        btn_signOut.setOnClickListener{
            auth.signOut() // metodo predefinito
            // si torna nel login e si "pulisce" il backstack
            var intent = Intent(this.requireContext(), Login::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            Toast.makeText(this.requireContext(), "Sign Out", Toast.LENGTH_SHORT).show()
        }
    }

    // Si prelevano i dati dell'allenatore
    private fun insertData(){
        val currentUser = auth.currentUser
        val uid = currentUser!!.uid
        db = FirebaseDatabase.getInstance("https://footballteam-d5795-default-rtdb.firebaseio.com/")
            .getReference("Users")
            .child(uid)

        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()) {
                    val user = snapshot.getValue(User::class.java)!!
                    username.text = user.name
                    email.text = user.email
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("TEST","Failed to read value")
            }
        })
    }
}