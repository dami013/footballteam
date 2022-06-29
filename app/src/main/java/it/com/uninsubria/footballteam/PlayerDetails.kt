package it.com.uninsubria.footballteam

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*


class PlayerDetails : AppCompatActivity() {
    private val fragmentDetails = visualize_players_details()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_details)
        val cf = intent.getStringExtra("cf")

        val transaction = supportFragmentManager.beginTransaction()
        val args = Bundle()
        args.putString("cf", cf)
        fragmentDetails.arguments = args
        transaction.replace(R.id.fragmentContainer,fragmentDetails).commit()
        }
    }
