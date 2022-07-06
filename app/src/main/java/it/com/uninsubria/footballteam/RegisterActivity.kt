package it.com.uninsubria.footballteam

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_atleti.*
import kotlinx.android.synthetic.main.fragment_info.*
import kotlinx.android.synthetic.main.register.*
import kotlinx.android.synthetic.main.register_player_fragment.*
import java.util.regex.Pattern


class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private var TAG = "SignUpActivity"
    private lateinit var userName: EditText
    private lateinit var password: EditText
    private lateinit var confirmPassword: EditText
    private lateinit var mail: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)
        userName = findViewById<EditText>(R.id.nomeUtente)
        password = findViewById<EditText>(R.id.pwd)
        confirmPassword= findViewById<EditText>(R.id.confirm_pw)
        mail = findViewById<EditText>(R.id.posta)

        login.setOnClickListener{
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        auth = Firebase.auth
        btn_Register.setOnClickListener{
            onSignUpClick()
        }
    }

    private fun onSignUpClick() {
        var check = true
        val mail = mail.text.toString().trim()
        val psw = password.text.toString().trim()
        val userName = userName.text.toString().trim()
        val confPw = confirmPassword.text.toString().trim()
        if (userName.isEmpty()) {
            nomeUtente.error = "Inserisci nome utente"
            check = false
        }
        if (mail.isEmpty()) {
            posta.error = "Inserisci E-mail"
            check = false
        }
        if(!isValidEmail(mail)){
            posta.error = "E-mail non vailida"
            check = false
        }
        if (psw.isEmpty() || psw.length <=4 ) {
            pwd.error = "Inserisci password di almeno 6 caratteri"
            check = false
        }
        if(!isValidPassword(psw)){
            pwd.error = "Password non valida"
            check = false
        }
        if(psw!=confPw){
            confirm_pw.error = "Le password non coincidono"
            check = false
        }
        if(check) {
            createUser(userName, mail, psw)
        } else {
            Toast.makeText(this,"Si controlli i campi",Toast.LENGTH_SHORT).show()
        }
    }

    private fun createUser(userName: String, email: String, password: String) {
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this){
            if (it.isSuccessful) {
                Log.d(TAG, "createUserWithEmail:success")
                val currentUser = auth.currentUser
                val uid = currentUser!!.uid
                val userMap = HashMap<String, String>()
                userMap["name"] = userName
                userMap["password"] = password
                userMap["email"] = email
                val database = FirebaseDatabase.getInstance().getReference("Users").child(uid)
                database.setValue(userMap).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val intent = Intent(applicationContext, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    else {
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else{
                try {
                    throw it.exception!!
                } catch (e: FirebaseAuthUserCollisionException) {
                    // email already in use
                    Toast.makeText(applicationContext, "Email already taken!", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        val EMAIL_PATTERN = ("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
        val pattern = Pattern.compile(EMAIL_PATTERN)
        val matcher = pattern.matcher(email)
        return matcher.matches()
    }

    private fun isValidPassword(pass: String?): Boolean {
        return pass != null && pass.length >= 4
    }
}
