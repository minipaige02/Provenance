package minipaige.example.provenance.com.Controller

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.archival_item.view.*
import minipaige.example.provenance.com.Model.ArchivalItem
import minipaige.example.provenance.com.R
import minipaige.example.provenance.com.Utilities.EXTRA_ARCHVIAL_ITEM

class HomeActivity : MainActivity() {
    lateinit var databaseRef: DatabaseReference
    lateinit var layoutManager: GridLayoutManager
    private var recyclerAdapter: adapter? = null
    var archivalItems: MutableList<ArchivalItem>? = null
    var archivalItemList: MutableList<ArchivalItem>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        welcomeTxt.text = "Welcome, ${username}!"

        databaseRef = FirebaseDatabase.getInstance().getReference(username)

        archivalItems = mutableListOf()
        archivalItemList = mutableListOf()

        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                if (dataSnapshot!!.exists()) {
                    listHeading.text = "Your documents"
                    for(item in dataSnapshot.children){
                        val archivalItem = item.getValue(ArchivalItem::class.java)
                        archivalItemList?.add(archivalItem!!)
                    }

                    archivalItemList = archivalItemList?.asReversed()
                    archivalItems!!.addAll(archivalItemList!!)

                    layoutManager = GridLayoutManager(this@HomeActivity, 3)
                    recyclerAdapter = adapter(this@HomeActivity, archivalItems!!)
                    recyclerView.layoutManager = layoutManager
                    recyclerView.adapter = recyclerAdapter

                    search!!.addTextChangedListener(object: TextWatcher {
                        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                           recyclerAdapter!!.getFilter().filter(s.toString())
                        }
                        override fun afterTextChanged(s: Editable?) {}
                    })

                } else {
                    search.visibility = View.GONE
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

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you sure you want to exit the app?")
        builder.setPositiveButton("Yes") { dialogInterface: DialogInterface, i: Int ->
            val loginActivity = Intent(this, LoginActivity::class.java)
            startActivity(loginActivity)
            finish()
        }

        builder.setNegativeButton("Cancel", { dialogInterface: DialogInterface, i: Int -> })
        builder.show()
    }

    private inner class adapter(internal var context: Context, internal var data: List<ArchivalItem>) : RecyclerView.Adapter<adapter.MyViewHolder>(),
        Filterable {

        internal var mfilter: NewFilter

        override fun getFilter(): Filter {
            return mfilter
        }

        init {
            mfilter = NewFilter(this@adapter)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): adapter.MyViewHolder {
            val view = LayoutInflater.from(context).inflate(R.layout.archival_item, parent, false)
            return MyViewHolder(view)
        }

        override fun getItemCount(): Int {
            return data.size
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val item = data[position]
            holder.setData(item, position)
        }

        inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            fun setData(item: ArchivalItem?, post: Int) {
                val picasso = Picasso.get()
                picasso.load(item!!.image).rotate(90F).into(itemView.itemImage)

                itemView.collection.text = item.collection

                if (item.box != "" && item.folder != "") {
                    itemView.container1.text = "Box: ${item.box}"
                    itemView.container2.text = "Folder: ${item!!.folder}"
                } else if (item.box != "") {
                    itemView.container1.text = "Box: ${item.box}"
                } else if (item.folder != "") {
                    itemView.container1.text = "Folder: ${item.folder}"
                } else {
                    itemView.container1.text = item.otherCntr
                }

                itemView.relLayout.setOnClickListener{
                    val itemDetailActivity = Intent(context, ItemDetailActivity::class.java)
                    itemDetailActivity.putExtra(EXTRA_ARCHVIAL_ITEM, item)
                    context.startActivity(itemDetailActivity)
                }
            }
        }

        inner class NewFilter(var mAdapter: adapter) : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                archivalItems!!.clear()
                val results = FilterResults()

                if (constraint?.length == 0) {
                    archivalItems!!.addAll(archivalItemList!!)
                } else {
                    for (item in archivalItemList!!) {
                        if (item.collection?.toLowerCase()?.contains(constraint.toString().toLowerCase()) as Boolean
                            || item.tags?.toLowerCase()?.contains(constraint.toString().toLowerCase()) as Boolean
                            || item.description?.toLowerCase()?.contains(constraint.toString().toLowerCase()) as Boolean
                            || item.repository?.toLowerCase()?.contains(constraint.toString().toLowerCase()) as Boolean ) {
                            archivalItems!!.add(item)
                        }
                    }
                }

                results.values = archivalItems
                results.count = archivalItems!!.size
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                this.mAdapter.notifyDataSetChanged()
            }

        }
    }
}
