package minipaige.example.provenance.com.Controller

import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*
import minipaige.example.provenance.com.R

class LoginActivity : MainActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        logInBtn.setOnClickListener {
            username = usrNmInput.text.toString().trim()

            if (username.contains(".") || username.contains("$") || username.contains("#") ||
                username.contains("[") || username.contains("]") || username.contains("/")) {
                usrNmInput.error = "Username cannot contain ., $, #, [, ], or / characters."
                usrNmInput.requestFocus()
                return@setOnClickListener

            } else if (username.isEmpty()){
                usrNmInput.error = "Please enter a valid username."
                usrNmInput.requestFocus()
                return@setOnClickListener
            } else {
                val homeActivity = Intent(this, HomeActivity::class.java)
                startActivity(homeActivity)

            }

        }

    }
}
