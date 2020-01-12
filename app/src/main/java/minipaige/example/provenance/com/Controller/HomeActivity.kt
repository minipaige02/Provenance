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
//    var testList: ArrayList<ArchivalItem> = ArrayList()

//    lateinit var itemList: ArrayList<ArchivalItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val databaseRef = FirebaseDatabase.getInstance().getReference(USERNAME)
        println(databaseRef)

        welcomeTxt.text = "Welcome, ${USERNAME}!"

        val layoutManager = GridLayoutManager(this, 3)
        recyclerView.layoutManager = layoutManager

        val adapter = ArchivalItemsAdapter(this, ArchivalItems.testList)
        recyclerView.adapter = adapter


//        createTestList()
//        println(testList)
//
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

//    fun createTestList() {
//        val newItem = ArchivalItem("Hoover", "Gahagan papers", "7", "", "", "", "", "https://firebasestorage.googleapis.com/v0/b/provenance-6fca1.appspot.com/o/uploads%2F96be606a-1695-4522-86c3-9ff945bc1196?alt=media&token=55b52c9a-d152-438f-9a58-8f7eddcd1e73")
//        val newItem2 = ArchivalItem("UC Berkeley", "Wolfe papers", "9", "10", "", "a sample description", "", "https://firebasestorage.googleapis.com/v0/b/provenance-6fca1.appspot.com/o/uploads%2Fe3c39ad2-3d87-4c52-95b5-8b7e896cc127?alt=media&token=fa4c9218-c392-4111-8ad3-b28de9d68256")
//        testList.add(newItem)
//        testList.add(newItem2)
//    }

}
