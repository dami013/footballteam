package it.com.uninsubria.footballteam.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import it.com.uninsubria.footballteam.R
import it.com.uninsubria.footballteam.visualize_players_details
import kotlinx.android.synthetic.main.fragment_change_player_details.*
import kotlinx.android.synthetic.main.fragment_visualize_players_details.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class Change_player_details : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val visualizePlayers = visualize_players_details()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        visualizza.setOnClickListener(View.OnClickListener {
            val fragmentManager = parentFragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(
                R.id.fragmentContainer,
                visualizePlayers
            )
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_change_player_details, container, false)

        return view
    }

}