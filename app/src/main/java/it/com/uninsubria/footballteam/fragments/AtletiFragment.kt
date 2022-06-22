package it.com.uninsubria.footballteam.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import it.com.uninsubria.footballteam.Atleta
import it.com.uninsubria.footballteam.R
import it.com.uninsubria.footballteam.adapter.PlayerAdapter
import kotlinx.android.synthetic.main.fragment_atleti.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AtletiFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class AtletiFragment : Fragment(){

    private var param1: String? = null
    private var param2: String? = null
    private lateinit var reg: RecyclerView
    private lateinit var list: ArrayList<Atleta>
    private  lateinit var db: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_atleti, container, false)


        reg = view.findViewById(R.id.recycler_view)
        reg.layoutManager = LinearLayoutManager(view.context)
        reg.setHasFixedSize(true)
        list = arrayListOf<Atleta>()
        readAtlethData()

        val fab = view.findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener{
            val nuovo = register_player_fragment()
            val fragmentManager = parentFragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(
                R.id.mainContainer,
                nuovo
            )
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()

        }
        return view
    }


    private fun readAtlethData() {
        val auth = Firebase.auth
        val currentUser = auth.currentUser
        val uid = currentUser!!.uid
        db = FirebaseDatabase.getInstance("https://footballteam-d5795-default-rtdb.firebaseio.com/")
            .getReference("Users")
            .child(uid)
            .child("Atleti")
        db.addValueEventListener(object :ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()) {
                    for(data in snapshot.children) {
                        val atleta = data.getValue(Atleta::class.java)
                        list.add(atleta!!)
                    }
                    reg.adapter = PlayerAdapter(list)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("TEST","Failed to read value")
            }
        })

    }
}

