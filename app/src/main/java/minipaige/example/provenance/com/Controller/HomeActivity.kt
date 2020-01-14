package minipaige.example.provenance.com.Controller

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_home.*
import minipaige.example.provenance.com.Model.ArchivalItem
import minipaige.example.provenance.com.Model.ArchivalItems
import minipaige.example.provenance.com.Model.ArchivalItemsAdapter
import minipaige.example.provenance.com.R
import minipaige.example.provenance.com.Utilities.USERNAME

class HomeActivity : MainActivity() {

//    lateinit var itemList: ArrayList<ArchivalItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val databaseRef = FirebaseDatabase.getInstance().getReference(USERNAME)
        println(databaseRef)

        welcomeTxt.text = "Welcome, ${USERNAME}!"

        //Set recyclerview
        val layoutManager = GridLayoutManager(this, 3)
        recyclerView.layoutManager = layoutManager

        //TODO: replace testList with data from database call
        val adapter = ArchivalItemsAdapter(this, ArchivalItems.testList)
        recyclerView.adapter = adapter


//        databaseRef.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                var t: GenericTypeIndicator<Map<String, ArchivalItem>> = object: GenericTypeIndicator<Map<String, ArchivalItem>>() {}
//
//                val value: Map<String, ArchivalItem>? = dataSnapshot.getValue(t)
//                Log.d(TAG, "Database value is: $value")
//
//
//                itemList = ArrayList(value?.values!!)
//
//                println(itemList)
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException())
//            }
//        })

//
//        addImgBtn.setOnClickListener {
//            val metadataActivity = Intent(this, MetadataActivity::class.java)
//            startActivity(metadataActivity)
//        }
    }

}
