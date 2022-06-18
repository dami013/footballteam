package it.com.uninsubria.footballteam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import it.com.uninsubria.footballteam.adapter.PlayerAdapter
import it.com.uninsubria.footballteam.fragments.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        Toast.makeText(this,"Benvenuto",Toast.LENGTH_SHORT).show()

        val atleti = AtletiFragment()
        val nuovo = register_player_fragment()
        val chat = ChatFragment()

        // Creazione Home
        // Modifica metodo precedentemente deprecato
        creazioneFragment(atleti)
        bottom_navigation.setOnItemSelectedListener  {
            when(it.itemId) {
                R.id.atleti -> creazioneFragment(atleti)
                R.id.nuovo -> creazioneFragment(nuovo)
                R.id.chat -> creazioneFragment(chat)
            }
            true
        }
    }

    private fun creazioneFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.mainContainer, fragment)
            commit()
        }

}