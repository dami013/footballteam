package it.com.uninsubria.footballteam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import it.com.uninsubria.footballteam.fragments.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), Communicator {

    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // Si vuole visualizzare la toolbar nella main activity
        toolbar = findViewById(R.id.toolbar)
        toolbar.title = "Football team"
        // stile della toolbar
        toolbar.setTitleTextAppearance(this,R.style.TextAppearance_AppCompat_Widget_ActionBar_Title)
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeButtonEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);
        // aggiunta di un logo alla toolbat
        supportActionBar?.setIcon(R.drawable.ic_soccer); //also displays wide logo


        // Messaggio di benvenuto quando si entra nella main
        Toast.makeText(this,"Benvenuto",Toast.LENGTH_SHORT).show()


        // Creazione dei fragment
        val atleti = AtletiFragment()
        val info = infoFragment()

        // All'avvio visualizza il fragment atleti, contenente i giocatori
        creazioneFragment(atleti)

        // meccanismo per la selezione del fragment nel menu
        bottom_navigation.setOnItemSelectedListener  {
            when(it.itemId) {
                R.id.atleti -> creazioneFragment(atleti)
                R.id.info -> creazioneFragment(info)
            }
            true
        }
    }

    // Metodo per passare da un fragment all'altro
    private fun creazioneFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.mainContainer, fragment)
            commit()
        }


    // passaggio dei giocatori selezionati alla chat
    // utilizzo interfaccia per comunicare dati tra fragments
    // maggiori dettagli nell'implementazione della chat
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