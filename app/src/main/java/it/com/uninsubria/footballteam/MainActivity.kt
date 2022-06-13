package it.com.uninsubria.footballteam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import it.com.uninsubria.footballteam.fragments.AtletiFragment
import it.com.uninsubria.footballteam.fragments.ChatFragment
import it.com.uninsubria.footballteam.fragments.HomeFragment
import it.com.uninsubria.footballteam.fragments.LockFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val home = HomeFragment()
        val atleti = AtletiFragment()
        val lock = LockFragment()
        val chat = ChatFragment()

        // Creazione Home
        creazioneFragment(home)
        bottom_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.home -> creazioneFragment(home)
                R.id.atleti -> creazioneFragment(atleti)
                R.id.lock -> creazioneFragment(lock)
                R.id.chat -> creazioneFragment(chat)
            }
            true
        }
    }

    private fun creazioneFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.contenitore, fragment)
            commit()
        }
}