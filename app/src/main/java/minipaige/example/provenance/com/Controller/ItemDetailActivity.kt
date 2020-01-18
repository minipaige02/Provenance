package minipaige.example.provenance.com.Controller

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
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

        editBtn.setOnClickListener{
            val updateActivity = Intent(this, UpdateActivity::class.java)
            updateActivity.putExtra(EXTRA_ARCHVIAL_ITEM, archivalItem)
            startActivity(updateActivity)
        }

        closeBtn.setOnClickListener {
            val homeActivity = Intent(this, HomeActivity::class.java)
            startActivity(homeActivity)
        }

        deleteBtn.setOnClickListener{
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Delete")
            builder.setMessage("Are you sure you want to permanently delete this item?")
            builder.setPositiveButton("Yes") { dialogInterface: DialogInterface, i: Int ->
                deleteItem()
            }

            builder.setNegativeButton("Cancel", { dialogInterface: DialogInterface, i: Int -> })
            builder.show()
        }
    }

    private fun deleteItem() {
        val databaseRef = FirebaseDatabase.getInstance().getReference(username)
        val itemRef = databaseRef.child(archivalItem.id!!)

        itemRef.removeValue().addOnSuccessListener {
            val imageRef = FirebaseStorage.getInstance().getReferenceFromUrl(archivalItem.image!!)
            imageRef.delete().addOnSuccessListener {
                Toast.makeText(this, "Item removed successfully.", Toast.LENGTH_LONG).show()

                val homeActivity = Intent(this, HomeActivity::class.java)
                startActivity(homeActivity)

            }.addOnFailureListener {
                Toast.makeText(this, "Image file could not be deleted.", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Item could not be deleted.", Toast.LENGTH_SHORT).show()
        }
    }
}
