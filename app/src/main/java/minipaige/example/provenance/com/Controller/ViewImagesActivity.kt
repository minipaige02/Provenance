package minipaige.example.provenance.com.Controller


import android.os.Bundle
import android.util.Log
import com.google.firebase.database.*
import minipaige.example.provenance.com.Model.ArchivalItem
import minipaige.example.provenance.com.R
import java.util.HashMap

class ViewImagesActivity : MainActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_images)

//        // DATABASE READ
//        val database = FirebaseDatabase.getInstance()
//        val myRef = database.getReference("Test")
//
//        // Read from the database
//        myRef.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                val value = dataSnapshot.getValue(ArchivalItem::class.java)
//                Log.d(TAG, "Value is: $value")
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException())
//            }
//        })
    }
}
