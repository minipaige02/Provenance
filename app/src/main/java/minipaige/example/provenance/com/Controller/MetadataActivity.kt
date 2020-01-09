package minipaige.example.provenance.com.Controller

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_metadata.*
import minipaige.example.provenance.com.Model.ArchivalItem
import minipaige.example.provenance.com.R
import minipaige.example.provenance.com.Utilities.EXTRA_ARCHVIAL_ITEM

class MetadataActivity : MainActivity() {
    var archivalItem = ArchivalItem("", "", "", "", "", "", arrayListOf(), "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_metadata)

        metadataNextBtn.setOnClickListener {
            // put in helper method?
            archivalItem.repository = repInput.text.toString()
            archivalItem.collection = colInput.text.toString()
            archivalItem.box = boxInput.text.toString()
            archivalItem.folder = folderInput.text.toString()
            archivalItem.otherCntr = otherCntrInput.text.toString()
            archivalItem.description = cntrDescInput.text.toString()
            val tags = tagsInput.text.toString().split("; ")
            //iterate over tags to add each tag to archivalItem.tags array
            //archivalItem.tags.add(tags.first())
//
//            for (tag in tags) {
//                archivalItem.tags.add("hello")
//            }
            println(tags)



            if (archivalItem.repository == "" ) {
                Toast.makeText(this, "Repository cannot be blank.", Toast.LENGTH_LONG).show()
            } else if (archivalItem.collection == "") {
                Toast.makeText(this, "Collection name cannot be blank.", Toast.LENGTH_LONG).show()
            } else if (archivalItem.box == "" && archivalItem.folder == "" && archivalItem.otherCntr == "") {
                Toast.makeText(this, "Box, folder, or other container must be filled out.", Toast.LENGTH_LONG).show()
            } else {
                // put in helper method?
                repInput.text.clear()
                colInput.text.clear()
                boxInput.text.clear()
                folderInput.text.clear()
                otherCntrInput.text.clear()
                cntrDescInput.text.clear()
                tagsInput.text.clear()

                val cameraActivity = Intent(this, CameraActivity::class.java)
                cameraActivity.putExtra(EXTRA_ARCHVIAL_ITEM, archivalItem)
                startActivity(cameraActivity)
            }

        }
    }


}
