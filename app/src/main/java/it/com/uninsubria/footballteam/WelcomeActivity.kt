package it.com.uninsubria.footballteam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class WelcomeActivity : AppCompatActivity() {
    lateinit var handler: Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)


        // thread per il passaggio del tempo e visualizzazione di un messaggio di benvenuto
        // viene aggiunto la durata di questo messaggio di benvenuto
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent (this, Login::class.java)
            startActivity(intent)
            finish()
        }, 1500)


    }
}