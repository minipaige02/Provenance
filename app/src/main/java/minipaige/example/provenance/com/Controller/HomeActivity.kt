package minipaige.example.provenance.com.Controller

import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_home.*
import minipaige.example.provenance.com.R

class HomeActivity : MainActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        addImgBtn.setOnClickListener {
            val metadataIntent = Intent(this, MetadataActivity::class.java)
            startActivity(metadataIntent)
        }

        viewImgBtn.setOnClickListener {
            val viewAllIntent = Intent(this, ViewImagesActivity::class.java)
            startActivity(viewAllIntent)
        }
    }

}
