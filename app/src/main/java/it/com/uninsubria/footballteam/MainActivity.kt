package it.com.uninsubria.footballteam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import it.com.uninsubria.footballteam.fragments.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), Communicator {

    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        toolbar.title = "Football team"
        toolbar.setTitleTextAppearance(this,R.style.TextAppearance_AppCompat_Widget_ActionBar_Title)
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeButtonEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);
        supportActionBar?.setIcon(R.drawable.ic_soccer); //also displays wide logo


        Toast.makeText(this,"Benvenuto",Toast.LENGTH_SHORT).show()

        val atleti = AtletiFragment()
        val info = infoFragment()

        // Creazione Home
        // Modifica metodo precedentemente deprecato
        creazioneFragment(atleti)

        bottom_navigation.setOnItemSelectedListener  {
            when(it.itemId) {
                R.id.atleti -> creazioneFragment(atleti)
                R.id.info -> creazioneFragment(info)
            }
            true
        }
    }

    private fun creazioneFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.mainContainer, fragment)
            commit()
        }

    override fun passData(data: ArrayList<Atleta>) {
        val bundle = Bundle()
        bundle.putParcelableArrayList("lista",data)
        val trans = this@MainActivity.supportFragmentManager.beginTransaction()
        val chat = ChatFragment()
        chat.arguments  = bundle
        trans.replace(R.id.mainContainer,chat)
        trans.addToBackStack(null)
        trans.commit()
    }
}