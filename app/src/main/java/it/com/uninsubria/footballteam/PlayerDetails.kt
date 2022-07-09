package it.com.uninsubria.footballteam

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


//activity con dentro un fragment
class PlayerDetails : AppCompatActivity() {

    private val fragmentDetails = Visualize_players_details()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_details)
        // riprendiamo il codice fiscale
        val cf = intent.getStringExtra("cf")

        val transaction = supportFragmentManager.beginTransaction()
        val args = Bundle()
        // passo il codice fiscale
        args.putString("cf", cf)
        fragmentDetails.arguments = args
        transaction.replace(R.id.fragmentContainer,fragmentDetails).commit()
        }
    }
