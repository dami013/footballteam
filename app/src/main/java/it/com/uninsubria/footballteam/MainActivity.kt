package it.com.uninsubria.footballteam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import it.com.uninsubria.footballteam.adapter.PlayerAdapter
import it.com.uninsubria.footballteam.fragments.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), Communicator {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Toast.makeText(this,"Benvenuto",Toast.LENGTH_SHORT).show()

        val atleti = AtletiFragment()
        val chat = ChatFragment()

        // Creazione Home
        // Modifica metodo precedentemente deprecato
        creazioneFragment(atleti)

        bottom_navigation.setOnItemSelectedListener  {
            when(it.itemId) {
                R.id.atleti -> creazioneFragment(atleti)
               // R.id.chat -> creazioneFragment(chat)
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
        bundle.putSerializable("list",data)
        val trans = this@MainActivity.supportFragmentManager.beginTransaction()
        val chat = ChatFragment()
        chat.arguments  = bundle
        trans.replace(R.id.mainContainer,chat)
        trans.addToBackStack(null)
        trans.commit()
    }
}