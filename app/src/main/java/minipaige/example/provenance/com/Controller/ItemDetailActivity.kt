package minipaige.example.provenance.com.Controller

import android.os.Bundle
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_item_detail.*
import minipaige.example.provenance.com.Model.ArchivalItem
import minipaige.example.provenance.com.R
import minipaige.example.provenance.com.Utilities.EXTRA_ARCHVIAL_ITEM

class ItemDetailActivity : MainActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_detail)

        val archivalItem = intent.getParcelableExtra<ArchivalItem>(EXTRA_ARCHVIAL_ITEM)

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
            println("Edit item button is being clicked")
            // val EditActivity = Intent(this, EditActivity::class.java)
            // EditActivity.putExtra(EXTRA_ARCHVIAL_ITEM, archivalItem)
            // startActivity(EditActivity)
        }



    }
}
