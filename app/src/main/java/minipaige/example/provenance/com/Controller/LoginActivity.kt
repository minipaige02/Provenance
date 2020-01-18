package minipaige.example.provenance.com.Controller

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import minipaige.example.provenance.com.R

class LoginActivity : MainActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        logInBtn.setOnClickListener {
            username = usrNmInput.text.toString().trim()

            if (username != "") {
                val homeActivity = Intent(this, HomeActivity::class.java)
                startActivity(homeActivity)
            } else {
                usrNmInput.error = "Please enter a valid username."
                usrNmInput.requestFocus()
                return@setOnClickListener
            }

        }

    }
}
