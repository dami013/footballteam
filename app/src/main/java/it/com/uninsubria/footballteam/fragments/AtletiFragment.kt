package it.com.uninsubria.footballteam.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import it.com.uninsubria.footballteam.Dataset
import it.com.uninsubria.footballteam.R
import it.com.uninsubria.footballteam.adapter.PlayerAdapter
import kotlinx.android.synthetic.main.fragment_atleti.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AtletiFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class AtletiFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    private val fragmentAtl = register_player_fragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_atleti, container, false)
        startRecyclerView(view)
        return view
    }

    private fun startRecyclerView(view: View) {
        val recycler = view.findViewById<RecyclerView>(R.id.recycler_view)
        val manager = LinearLayoutManager(view.context)
        val decorazione = DividerItemDecoration(view.context,manager.orientation)
        recycler.layoutManager = manager
        recycler.adapter = PlayerAdapter(Dataset.giocatori)
        recycler.addItemDecoration(decorazione)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AtletiFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AtletiFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}

