package minipaige.example.provenance.com.Controller

import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_welcome.*
import minipaige.example.provenance.com.R

class WelcomeActivity : MainActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        logInBtn.setOnClickListener {
            val homeIntent = Intent(this, HomeActivity::class.java)
            startActivity(homeIntent)
        }

        newAcctBtn.setOnClickListener {
            val newAcctIntent = Intent(this, NewAcctActivity::class.java)
            startActivity(newAcctIntent)
        }
    }
}
