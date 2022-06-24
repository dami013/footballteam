package it.com.uninsubria.footballteam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.*


class PlayerDetails : AppCompatActivity() {
    private  lateinit var db: DatabaseReference
    private val fragmentDetails = viusalize_players_details()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_details)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainerView,fragmentDetails).commit()
        val cf = intent.getStringExtra("cf")
       // insertData(cf!!)
    }


}
