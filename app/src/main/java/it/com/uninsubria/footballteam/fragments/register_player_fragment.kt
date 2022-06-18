package it.com.uninsubria.footballteam.fragments

import android.app.Activity
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import it.com.uninsubria.footballteam.R
import kotlinx.android.synthetic.main.register.*
import kotlinx.android.synthetic.main.register_player_fragment.*
import kotlinx.android.synthetic.main.register_player_fragment.view.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class register_player_fragment : Fragment(){
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val FOTO = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View =  inflater.inflate(R.layout.register_player_fragment, container, false)

        view.immagine.setOnClickListener{
            openGalleryForImage()
        }

        view.register.setOnClickListener{
            onRegisterClick()
        }

        return view
    }

    private fun onRegisterClick() {
        val name = nome.text.toString().trim()
        val cogn = cognome.text.toString().trim()
        val image = immagine.resources.toString()
        val dataN = dataNascita.text.toString().trim()
        val cel = phone.text.toString().trim()
        val cert = certificazione?.text.toString().trim()  //certificazioni e risultati possono essere nulli
        val ris = risultati?.text.toString().trim()

        if(name.isEmpty()){
            nome.error = "inserire nome"
            return
        }
        if(cogn.isEmpty()){
            cognome.error = "inserire cognome"
            return
        }
        if(dataN.isEmpty()){
            dataNascita.error = "inserire data di nascita"
            return
        }
        if(cel.isEmpty()){
            phone.error = "inserire numerop di telefono"
            return
        }

        saveData(name,cogn,dataN,cel,cert,ris,image)
    }

    private fun saveData(name: String, cogn: String, dataN: String, cel: String, cert: String, ris: String, image: String) {

    }


    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, FOTO)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == FOTO) {
            immagine.setImageURI(data?.data) // handle chosen image
        }
    }
}