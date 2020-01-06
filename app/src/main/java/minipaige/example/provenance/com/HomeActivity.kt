package minipaige.example.provenance.com

import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : MainActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        captureImgBtn.setOnClickListener {
            val metadataIntent = Intent(this, MetadataActivity::class.java)
            startActivity(metadataIntent)
        }
    }

}
