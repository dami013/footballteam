package it.com.uninsubria.footballteam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import it.com.uninsubria.footballteam.adapter.PlayerAdapter

class PlayerDetails : AppCompatActivity() {
    private  lateinit var db: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_details)
        val cf = intent.getStringExtra("cf")

    }


}