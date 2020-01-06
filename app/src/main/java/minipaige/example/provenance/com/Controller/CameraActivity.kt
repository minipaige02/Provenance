package minipaige.example.provenance.com.Controller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_camera.*
import minipaige.example.provenance.com.R

class CameraActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        camImage.setOnClickListener{
            val homeIntent = Intent(this, HomeActivity::class.java)
            startActivity(homeIntent)
        }

        checkImg.setOnClickListener{
            //TO DO create images

            //val viewAllIntent = Intent(this, ViewImagesActivity::class.java)
            //startActivity(viewAllIntent)
        }
    }


}
