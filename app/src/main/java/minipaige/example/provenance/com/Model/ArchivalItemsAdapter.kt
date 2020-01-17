package minipaige.example.provenance.com.Model

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.archival_item.view.*
import minipaige.example.provenance.com.Controller.ItemDetailActivity
import minipaige.example.provenance.com.R
import minipaige.example.provenance.com.Utilities.EXTRA_ARCHVIAL_ITEM

class ArchivalItemsAdapter(val context: Context, val archivalItems: List<ArchivalItem>) : RecyclerView.Adapter<ArchivalItemsAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.archival_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
       return archivalItems.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //if archivalItems.isEmpty(), then
        val item = archivalItems[position]
        holder.setData(item, position)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun setData(item: ArchivalItem?, post: Int) {
            val picasso = Picasso.get()
            picasso.load(item!!.image).rotate(90F).into(itemView.itemImage)

            itemView.collection.text = item!!.collection

            if (item.box != "" && item.folder != "") {
                itemView.container1.text = "Box: ${item.box}"
                itemView.container2.text = "Folder: ${item!!.folder}"
            } else if (item.box != "") {
                itemView.container1.text = "Box: ${item.box}"
            } else if (item.folder != "") {
                itemView.container1.text = "Folder: ${item!!.folder}"
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

}