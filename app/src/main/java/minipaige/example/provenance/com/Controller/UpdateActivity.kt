package minipaige.example.provenance.com.Controller

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_update.*
import minipaige.example.provenance.com.Model.ArchivalItem
import minipaige.example.provenance.com.R
import minipaige.example.provenance.com.Utilities.EXTRA_ARCHVIAL_ITEM

class UpdateActivity : MainActivity() {
    lateinit var archivalItem: ArchivalItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        archivalItem = intent.getParcelableExtra<ArchivalItem>(EXTRA_ARCHVIAL_ITEM)

        val picasso = Picasso.get()
        picasso.load(archivalItem!!.image).rotate(90F).into(imageU)

        repUTxt.setText(archivalItem.repository)
        colUTxt.setText(archivalItem.collection)
        boxUTxt.setText(archivalItem.box)
        folderUTxt.setText(archivalItem.folder)
        otherCntrUTxt.setText(archivalItem.otherCntr)
        descUTxt.setText(archivalItem.description)
        tagsUTxt.setText(archivalItem.tags)


        updateBtn.setOnClickListener{
            if (saveItem()) {
                val itemDetailActivity = Intent(this, ItemDetailActivity::class.java)
                itemDetailActivity.putExtra(EXTRA_ARCHVIAL_ITEM, archivalItem)
                startActivity(itemDetailActivity)
            }
        }

        closeBtn.setOnClickListener{
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Are you sure you want to exit?")
            builder.setMessage("Any changes you have made will be lost. Are you sure you want to proceed?")
            builder.setPositiveButton("Yes") { dialogInterface: DialogInterface, i: Int ->
                val itemDetailActivity = Intent(this, ItemDetailActivity::class.java)
                itemDetailActivity.putExtra(EXTRA_ARCHVIAL_ITEM, archivalItem)
                startActivity(itemDetailActivity)
            }

            builder.setNegativeButton("Cancel", { dialogInterface: DialogInterface, i: Int -> })
            builder.show()
        }
    }

    private fun saveItem(): Boolean {
        val databaseRef = FirebaseDatabase.getInstance().getReference(username)

        val repository = repUTxt.text.toString().trim()
        val collection = colUTxt.text.toString().trim()
        val box = boxUTxt.text.toString().trim()
        val folder = folderUTxt.text.toString().trim()
        val otherCntr = otherCntrUTxt.text.toString().trim()
        val description = descUTxt.text.toString().trim()
        val tags = tagsUTxt.text.toString().trim()

        //validations
        if (repository.isEmpty()) {
            repUTxt.error = "Repository cannot be blank"
            repUTxt.requestFocus()
            return false
        } else if (collection.isEmpty()) {
            colUTxt.error = "Collection name cannot be blank."
            colUTxt.requestFocus()
            return false
        } else if (box.isEmpty() && folder.isEmpty() && otherCntr.isEmpty()) {
            boxUTxt.error = "Please enter either box, folder, or other container description."
            boxUTxt.requestFocus()
            return false
        }

        archivalItem = ArchivalItem(archivalItem.id, repository, collection, box, folder, otherCntr, description, tags, archivalItem.image)
        databaseRef.child(archivalItem.id!!).setValue(archivalItem)

        Toast.makeText(this, "Item updated.", Toast.LENGTH_SHORT).show()

        return true
    }
}
