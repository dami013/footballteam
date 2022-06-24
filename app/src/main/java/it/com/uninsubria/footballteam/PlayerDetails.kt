package it.com.uninsubria.footballteam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.*
import it.com.uninsubria.footballteam.fragments.Change_player_details
import kotlinx.android.synthetic.main.fragment_visualize_players_details.*


class PlayerDetails : AppCompatActivity() {
    private  lateinit var db: DatabaseReference
    private val fragmentDetails = viusalize_players_details()
    private val changeDetails = Change_player_details()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_details)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainerView,fragmentDetails).commit()
        val cf = intent.getStringExtra("cf")

        modifica.setOnClickListener{
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragmentContainerView, changeDetails)
                commit()
            }
        }
       // insertData(cf!!)
    }


}
