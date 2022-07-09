package it.com.uninsubria.footballteam.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import it.com.uninsubria.footballteam.Atleta
import it.com.uninsubria.footballteam.Communicator
import it.com.uninsubria.footballteam.R
import it.com.uninsubria.footballteam.adapter.PlayerAdapter
import it.com.uninsubria.footballteam.adapter.SwipeToDeleteCallback
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_atleti.*
import kotlinx.android.synthetic.main.fragment_atleti.view.*
import kotlinx.android.synthetic.main.giocatore.*


class AtletiFragment : Fragment(){

    // definizione delle animazioni del FAB(Floating Action Button)
    // utilizzo di lazy, in quanto richiede tempo e l'animazione è un'operazione pesante
    private val open : Animation by lazy { AnimationUtils.loadAnimation(context,R.anim.rotate_open_anim) }
    private val close : Animation by lazy { AnimationUtils.loadAnimation(context,R.anim.rotate_close_anim) }
    private val fromBottom : Animation by lazy { AnimationUtils.loadAnimation(context,R.anim.from_bottom_anim) }
    private val toBottom : Animation by lazy { AnimationUtils.loadAnimation(context,R.anim.to_bottom_anim) }

    private var clicked = false
    private lateinit var com : Communicator
    private lateinit var reg: RecyclerView
    private lateinit var list: ArrayList<Atleta>
    private lateinit var selezionati: ArrayList<Atleta>
    private lateinit var progressBar: ProgressBar
    private var db: DatabaseReference = FirebaseDatabase.getInstance("https://footballteam-d5795-default-rtdb.firebaseio.com/")
        .getReference("Users")
        .child(Firebase.auth.currentUser!!.uid)
        .child("Atleti")


    //callback simile a onCreate per le activity
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_atleti, container, false)
    }


    // callback chiamata quando sono pronti tutti gli elementi grafici
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Predisposizione recycler view
        setupRecyclerView(view)
        // Funzione per leggere i giocatori
        readPlayers()
        // Funzione per Eliminare i Giocatori
        deletePlayer()
        // Apertura registrazione di un'atleta con floating button
        openFunction(view)
    }

    // Legg i giocatori dal database
    private fun readPlayers() {
        db.addValueEventListener(object :ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()

                // progress bar non visisbile
                progressBar.visibility = View.GONE
                if(snapshot.exists()) {
                    // scorriamo su tutti i dati in Atleti di quell'utente
                    for(data in snapshot.children) {
                        //  atleta associato alla classe Atleta
                            val atleta = data.getValue(Atleta::class.java)
                        // aggiunta in una lista di atleti
                            list.add(atleta!!)
                        }

                    // Associamo all'adapter della recycler view, un nuovo adapter definito con la lista di atleti presenti
                    // utilizzo di una funzione di ordine superiore per passare dagli atleti presenti ad un sottoinsieme su cui inviare un messaggio
                    reg.adapter = PlayerAdapter(list, context!!) { position ->
                        val a: Atleta = list[position] // identifichiamo l'atleta

                        if(selezionati.contains(a)) {
                            Log.d("SELEZ",selezionati.toString())
                            selezionati.remove(a)
                            Log.d("SELEZ",selezionati.toString())
                            Toast.makeText(view?.context,"Eliminato",Toast.LENGTH_SHORT).show()
                            selezionati.remove(a)
                            Log.d("SELEZ",selezionati.toString())


                        }else{
                        selezionati.add(a)
                            Toast.makeText(view?.context,"Aggiunto",Toast.LENGTH_SHORT).show()
                        }
                    }// una volta caricati tutti gli atleti , la progress bar "scompare"
                    progressBar.visibility = View.GONE
                }
            }

            // in caso di operazione non avvenuta correttamente
            override fun onCancelled(error: DatabaseError) {
                Log.w("TEST",error.message)
            }
        })
    }

    // Eliminazione di un giocatore dal database mediante swipe
    private fun deletePlayer() {
        val deleteElement = object: SwipeToDeleteCallback() {
            // implementazione metodo on swipe
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // identifica la posizione
                val position = viewHolder.adapterPosition
                // rimuove l'atleta dalla lista
                val a: Atleta = list.removeAt(position)

                // Rimozione Giocatore nel database, sfruttando il codice fiscale
                a.codiceFiscale?.let { db.child(it).removeValue() }
                // Rimozione immagine dal firebase storage
                val path = "/image/${a.nome}${a.cognome}"
                Firebase.storage.reference.child(path).delete()

                // "notifico" all'adapter che ho rimosso un atelta
                reg.adapter?.notifyItemRemoved(position)
                // notifico cambiamento range
                reg.adapter?.notifyItemRangeChanged(position, list.size)
            }
        }
        // Sistema di gestione dello swipe
        val itemTouchHelper = ItemTouchHelper(deleteElement)
        itemTouchHelper.attachToRecyclerView(reg)
    }

    //
    private fun setupRecyclerView(view: View) {
        reg = view.findViewById(R.id.recycler_view)
        progressBar = view.findViewById(R.id.caricamentoBarra)
        list = arrayListOf<Atleta>()
        selezionati = arrayListOf<Atleta>()
        reg.apply {
            layoutManager = LinearLayoutManager(view.context)
            reg.setHasFixedSize(true) // per ottimizzare la recycler view
        }

    }

    // Gestione del FAB
    private fun openFunction(view: View) {
        // Di default sono invisibili, tranne il fab che apre gli altri
        val fab = view.findViewById<FloatingActionButton>(R.id.fab)
        val fabChat = view.findViewById<FloatingActionButton>(R.id.fab_chat)
        val fabAdd = view.findViewById<FloatingActionButton>(R.id.fab_add)
        // quando apri il principale , apri gli altri due
        fab.setOnClickListener{
            onAddButtonClicked()
        }
        // permette di inserire un nuovo giocatore
        fabAdd.setOnClickListener {
            val nuovo = register_player_fragment()
            creazioneFragment(nuovo)
        }
        // utilizza l'interfaccia per passare gli atleti selezionati al fragment chat
        fabChat.setOnClickListener {
            com = activity as Communicator
                com.passData(selezionati)
        }
    }

    // attivi le animazioni di apertura degli ulteriori FAB
    private fun onAddButtonClicked() {
        setVisibility(clicked)
        setAnimation(clicked)
        clicked = !clicked
    }

    private fun setAnimation(clicked: Boolean) {
        val fab = view?.findViewById<FloatingActionButton>(R.id.fab)
        val fabChat = view?.findViewById<FloatingActionButton>(R.id.fab_chat)
        val fabAdd = view?.findViewById<FloatingActionButton>(R.id.fab_add)
        if(!clicked) {
            // apertura fab
            fabChat?.startAnimation(fromBottom)
            fabAdd?.startAnimation(fromBottom)
            fab?.startAnimation(open)
        } else {
            //chisura fab
            fabChat?.startAnimation(toBottom)
            fabAdd?.startAnimation(toBottom)
            fab?.startAnimation(close)
        }

    }

    private fun setVisibility(clicked: Boolean) {
        val fabChat = view?.findViewById<FloatingActionButton>(R.id.fab_chat)
        val fabAdd = view?.findViewById<FloatingActionButton>(R.id.fab_add)
        if(!clicked) {
            // rendi visibili i fab
            fabChat?.visibility = View.VISIBLE
            fabAdd?.visibility = View.VISIBLE
        }
        else {
            // pone invisibili i fab
            fabChat?.visibility = View.INVISIBLE
            fabAdd?.visibility = View.INVISIBLE
        }
    }
    // metodo per passare fa un fragment all'altro
    private fun creazioneFragment(fragment: Fragment) =
        parentFragmentManager.beginTransaction().apply {
            replace(R.id.mainContainer, fragment)
            addToBackStack(null)
            commit()
        }
}
