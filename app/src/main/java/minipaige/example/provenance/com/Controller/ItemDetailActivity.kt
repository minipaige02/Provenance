package minipaige.example.provenance.com.Controller

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_item_detail.*
import kotlinx.android.synthetic.main.activity_item_detail.imageD
import minipaige.example.provenance.com.Model.ArchivalItem
import minipaige.example.provenance.com.R
import minipaige.example.provenance.com.Utilities.EXTRA_ARCHVIAL_ITEM

class ItemDetailActivity : MainActivity() {
    lateinit var archivalItem: ArchivalItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_detail)

        archivalItem = intent.getParcelableExtra<ArchivalItem>(EXTRA_ARCHVIAL_ITEM)

        if (archivalItem != null) {
            lateinit var container: String

            val picasso = Picasso.get()
            picasso.load(archivalItem!!.image).rotate(90F).into(imageD)

            repDTxt.text = archivalItem.repository
            colDTxt.text = archivalItem.collection
            if (archivalItem.box != "" && archivalItem.folder != "") {
                container = "Box ${archivalItem.box}, Folder ${archivalItem.folder}"
            } else if (archivalItem.box != "") {
                container = "Box ${archivalItem.box}"
            } else if (archivalItem.folder != "") {
                container = "Folder ${archivalItem.folder}"
            } else {
                container = archivalItem.otherCntr!!
            }
            cntrDTxt.text = container
            descDTxt.text = archivalItem.description
            tagsDTxt.text = archivalItem.tags
            citationTxt.text = "[Description of document]. [Date]. ${container}. ${archivalItem.collection}. ${archivalItem.repository}."
        }

        editBtn.setOnClickListener{
            val updateActivity = Intent(this, UpdateActivity::class.java)
            updateActivity.putExtra(EXTRA_ARCHVIAL_ITEM, archivalItem)
            startActivity(updateActivity)
        }
    }

//    fun openUpdateDiaglog(archivalItem: ArchivalItem) {
//        println("I am inside the update function")
//        println("${archivalItem.repository}")
//
//
//        repUTxt.setText(archivalItem.repository)
//        colUTxt.setText(archivalItem.collection)
//        if (archivalItem.box != "") {
//            boxUTxt.setText(archivalItem.box)
//        }
//
//        if (archivalItem.folder != "") {
//            folderUTxt.setText(archivalItem.folder)
//        }
//
//        if (archivalItem.otherCntr != "") {
//            otherCntrUTxt.setText(archivalItem.otherCntr)
//        }
//
//        if (archivalItem.description != "") {
//            descUTxt.setText(archivalItem.description)
//        }
//
//        if (archivalItem.tags != "") {
//            tagsUTxt.setText(archivalItem.tags)
//        }
//
//        builder.setView(view)
//        val alert = builder.create()
//        alert.show()

//        //Set default values for edit text fields


//        updateBtn.setOnClickListener {
////            val databaseRef = FirebaseDatabase.getInstance().getReference(username)
////
////            val repository = repUTxt.text.toString().trim()
////            val collection = colUTxt.text.toString().trim()
////            val box = boxUTxt.text.toString().trim()
////            val folder = folderUTxt.text.toString().trim()
////            val otherCntr = otherCntrUTxt.text.toString().trim()
////            val description = descUTxt.text.toString().trim()
////            val tags = tagsUTxt.text.toString().trim()
////
////            //validations
////            if (repository.isEmpty()) {
////                repUTxt.error = "Please enter a repository."
////                repUTxt.requestFocus()
////                return@setOnClickListener
////            } else if (collection.isEmpty()) {
////                colUTxt.error = "Please enter a collection name."
////                colUTxt.requestFocus()
////                return@setOnClickListener
////            } else if (box.isEmpty() && folder.isEmpty() && otherCntr.isEmpty()) {
////                boxUTxt.error = "Please enter either box, folder, or other container description."
////                boxUTxt.requestFocus()
////                return@setOnClickListener
////            }
////
////            val updatedArchivalItem = ArchivalItem(archivalItem.id, repository, collection, box, folder, otherCntr, description, tags, archivalItem.image)
////            databaseRef.child(archivalItem.id!!).setValue(updatedArchivalItem)
////
////            Toast.makeText(this, "Item updated.", Toast.LENGTH_SHORT).show()
//
//        }
}
