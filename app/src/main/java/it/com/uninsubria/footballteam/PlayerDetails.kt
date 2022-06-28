package it.com.uninsubria.footballteam

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import it.com.uninsubria.footballteam.fragments.Change_player_details
import kotlinx.android.synthetic.main.fragment_visualize_players_details.*


class PlayerDetails : AppCompatActivity() {
    private  lateinit var db: DatabaseReference
    private val fragmentDetails = visualize_players_details()
    private val changeDetails = Change_player_details()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_details)
        val cf = intent.getStringExtra("cf")

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer,fragmentDetails).commit()


        //passaggio dati da activity a fragment
        val args = Bundle()
        args.putString("cf", cf)
        fragmentDetails.setArguments(args)
        }
    }
