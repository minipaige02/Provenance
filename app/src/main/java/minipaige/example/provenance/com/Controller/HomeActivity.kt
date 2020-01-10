package minipaige.example.provenance.com.Controller

import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_home.*
import minipaige.example.provenance.com.R
import minipaige.example.provenance.com.Utilities.USERNAME

class HomeActivity : MainActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        welcomeTxt.text = "Welcome, ${USERNAME}!"

        addImgBtn.setOnClickListener {
            val metadataActivity = Intent(this, MetadataActivity::class.java)
            startActivity(metadataActivity)
        }
    }

}
