package minipaige.example.provenance.com.Controller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_camera.*
import minipaige.example.provenance.com.Model.ArchivalItem
import minipaige.example.provenance.com.R
import minipaige.example.provenance.com.Utilities.EXTRA_ARCHVIAL_ITEM

class CameraActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        val archivalItem = intent.getParcelableExtra<ArchivalItem>(EXTRA_ARCHVIAL_ITEM)
        println(archivalItem.repository)

        homeImg.setOnClickListener{
            val homeIntent = Intent(this, HomeActivity::class.java)
            startActivity(homeIntent)
        }

        checkImg.setOnClickListener{
            //TO DO create images

            val viewAllIntent = Intent(this, ViewImagesActivity::class.java)
            startActivity(viewAllIntent)
        }

        camImg.setOnClickListener{
            println("I'm taking photos!")
        }


    }


}
