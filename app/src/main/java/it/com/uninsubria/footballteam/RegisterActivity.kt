package it.com.uninsubria.footballteam

import CheckEmailPassword
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.login.*
import kotlinx.android.synthetic.main.register.*

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private var TAG = "SignUpActivity"
    private val check = CheckEmailPassword()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)

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
        val mail = email.text.toString().trim()
        val psw = et_password.text.toString().trim()
        val userName = name.text.toString().trim()
        if (userName.isEmpty()) {
            name.error = "Enter userName"
            return
        }
        if (mail.isEmpty()) {
            email.error = "Enter email"
            return
        }
        if(!check.isValidEmail(mail)){
            email.error = "invalid email"
            return
        }
        if (psw.isEmpty()) {
            et_password.error = "Enter password"
            return
        }
        if(!check.isValidPassword(psw)){
            et_password.error = "invalid password"
            return
        }
        createUser(userName, mail, psw)
    }

    private fun createUser(userName: String, email: String, password: String) {
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this){ task->
            if (task.isSuccessful) {
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
        }
    }
}
