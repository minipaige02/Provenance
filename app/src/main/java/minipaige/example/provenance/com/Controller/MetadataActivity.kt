package minipaige.example.provenance.com.Controller

import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_metadata.*
import minipaige.example.provenance.com.R

class MetadataActivity : MainActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_metadata)

        metadataNextBtn.setOnClickListener {
            // TO DO
            val repository = repInput.text.toString()
            val collection = colInput.text.toString()
            val box = boxInput.text.toString().toInt()
            val folder = folderInput.text.toString().toInt()
            val other = otherCntrInput.text.toString()
            val desc = cntrDescInput.text.toString()
            val tags = tagsInput.text.toString()

            println(repository)
            println(collection)
            println(box)
            println(folder)
            println(other)
            println(desc)
            println(tags)

            repInput.text.clear()
            colInput.text.clear()
            boxInput.text.clear()
            folderInput.text.clear()
            otherCntrInput.text.clear()
            cntrDescInput.text.clear()
            tagsInput.text.clear()

            val cameraIntent = Intent(this, CameraActivity::class.java)
            startActivity(cameraIntent)

        }
    }


}
