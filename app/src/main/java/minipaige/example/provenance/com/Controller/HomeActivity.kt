package minipaige.example.provenance.com.Controller

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.view.marginTop
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_home.*
import minipaige.example.provenance.com.Model.ArchivalItem
import minipaige.example.provenance.com.Adapter.ArchivalItemsAdapter
import minipaige.example.provenance.com.R

class HomeActivity : MainActivity() {
    lateinit var databaseRef: DatabaseReference
    lateinit var archivalItemsList: MutableList<ArchivalItem>
    lateinit var layoutManager: GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        welcomeTxt.text = "Welcome, ${username}!"

        archivalItemsList = mutableListOf()
        databaseRef = FirebaseDatabase.getInstance().getReference(username)

        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                if (dataSnapshot!!.exists()) {
                    listHeading.text = "Your documents"
                    archivalItemsList.clear()
                    for(item in dataSnapshot.children){
                        val archivalItem = item.getValue(ArchivalItem::class.java)
                        archivalItemsList.add(archivalItem!!)
                    }
                    archivalItemsList = archivalItemsList.asReversed()

                    layoutManager = GridLayoutManager(this@HomeActivity, 3)
                    recyclerView.layoutManager = layoutManager

                    val adapter =
                        ArchivalItemsAdapter(
                            this@HomeActivity,
                            archivalItemsList
                        )
                    recyclerView.adapter = adapter
                } else {
                    listHeading.setTextColor(Color.GRAY)
                    listHeading.text = "Click the camera icon to add documents!"
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
                Toast.makeText(this@HomeActivity, "Error retrieving items from database.", Toast.LENGTH_LONG).show()
            }
        })

        addImgBtn.setOnClickListener {
            val metadataActivity = Intent(this, MetadataActivity::class.java)
            startActivity(metadataActivity)
        }
    }

}
