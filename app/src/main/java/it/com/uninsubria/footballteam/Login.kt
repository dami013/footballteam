package it.com.uninsubria.footballteam

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.fragment_sign_in.*
import kotlinx.android.synthetic.main.login.*
import kotlinx.android.synthetic.main.login.btn_SignIn

class Login : AppCompatActivity() {
    private val signIn = SignIn()
    private val signUp = SignUp()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        btn_SignUp.setOnClickListener{
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainerView,signUp)
            transaction.commit()
        }

        btn_SignIn.setOnClickListener{
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainerView,signIn)
            transaction.commit()
        }



    }

}