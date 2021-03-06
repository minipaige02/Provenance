package minipaige.example.provenance.com.Controller

import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_metadata.*
import minipaige.example.provenance.com.Model.ArchivalItem
import minipaige.example.provenance.com.R
import minipaige.example.provenance.com.Utilities.EXTRA_ARCHVIAL_ITEM

class MetadataActivity : MainActivity() {
    var archivalItem = ArchivalItem("","", "", "", "", "", "", "", "", "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_metadata)

        metadataNextBtn.setOnClickListener {
            createArchivalItem()

            if (archivalItem.repository == "" ) {
                repInput.error = "Repository cannot be blank."
                repInput.requestFocus()
                return@setOnClickListener
            } else if (archivalItem.collection == "") {
                colInput.error = "Collection name cannot be blank."
                colInput.requestFocus()
                return@setOnClickListener
            } else if (archivalItem.box == "" && archivalItem.folder == "" && archivalItem.otherCntr == "") {
                boxInput.error = "Please enter either box, folder, or other container description."
                boxInput.requestFocus()
                return@setOnClickListener
            } else {
                clearInputs()

                val cameraActivity = Intent(this, CameraActivity::class.java)
                cameraActivity.putExtra(EXTRA_ARCHVIAL_ITEM, archivalItem)
                startActivity(cameraActivity)
            }
        }
    }

    private fun createArchivalItem() {
        archivalItem.repository = repInput.text.toString().trim()
        archivalItem.collection = colInput.text.toString().trim()
        archivalItem.box = boxInput.text.toString().trim()
        archivalItem.folder = folderInput.text.toString().trim()
        archivalItem.otherCntr = otherCntrInput.text.toString().trim()
        archivalItem.tags = tagsInput.text.toString().trim()
    }

    private fun clearInputs() {
        repInput.text.clear()
        colInput.text.clear()
        boxInput.text.clear()
        folderInput.text.clear()
        otherCntrInput.text.clear()
        tagsInput.text.clear()

    }

}
