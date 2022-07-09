package it.com.uninsubria.footballteam

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_atleti.*
import kotlinx.android.synthetic.main.login.*
import java.util.regex.Pattern


class Login : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private var TAG = "LoginActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        // proprietà di firebase
        auth = Firebase.auth

        // Apre il form di registrazione di un utente
        signUp.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
        // Controllo e apertura main activity
        btn_SignIn.setOnClickListener {
            onLoginClick()
        }
    }

    private fun onLoginClick() {
        val mail = et_email.text.toString().trim()
        val pw = password.text.toString().trim()

        if (mail.isEmpty()||!isValidEmail(mail)) {
            // compare un messaggio di errore
            et_email.error = "Password vuota o non corretta"
            return
        }
        if (pw.isEmpty()||!isValidPassword(pw)) {
            password.error = "Password vuota o non corretta"
            return
        }
        // funzione per effetuare il login
        loginUser(mail, pw)
    }

    private fun loginUser(name: String, pw: String) {
        // utilizzo metodo della classe FirebaseAuth, per autentificarsi con mail e password
        // listener che si attiva al completamento del task
        auth.signInWithEmailAndPassword(name,pw).addOnCompleteListener(this){ task ->

            if(task.isSuccessful){
                Log.d(TAG, "signInWithEmail:success")
                // Si apre la main activity
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                try {
                    // Eccezione generica catturata
                    throw task.exception!!
                } catch (e: FirebaseAuthException) {
                    // Stampiamo a schermo un messaggio che identifichi il non corretto avvenimento del login
                    Toast.makeText(applicationContext, "login failed", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    // controllo per la validità della mail
    private fun isValidEmail(email: String): Boolean {
        val EMAIL_PATTERN = ("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
        val pattern = Pattern.compile(EMAIL_PATTERN)
        val matcher = pattern.matcher(email)
        return matcher.matches()
    }

    // controllo password
    private fun isValidPassword(pass: String?): Boolean {
        return pass != null && pass.length >= 4
    }
}