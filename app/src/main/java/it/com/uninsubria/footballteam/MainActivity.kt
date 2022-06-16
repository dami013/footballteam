package it.com.uninsubria.footballteam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import it.com.uninsubria.footballteam.fragments.AtletiFragment
import it.com.uninsubria.footballteam.fragments.ChatFragment
import it.com.uninsubria.footballteam.fragments.HomeFragment
import it.com.uninsubria.footballteam.fragments.LockFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        Toast.makeText(this,"Benvenuto",Toast.LENGTH_SHORT).show()

        val home = HomeFragment()
        val atleti = AtletiFragment()
        val lock = LockFragment()
        val chat = ChatFragment()

        // Creazione Home
        creazioneFragment(home)
        // Modifica metodo precedentemente deprecato
        bottom_navigation.setOnItemSelectedListener  {
            when(it.itemId) {
                R.id.homeFrag -> creazioneFragment(home)
                R.id.atleti -> creazioneFragment(atleti)
                R.id.lock -> creazioneFragment(lock)
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