package minipaige.example.provenance.com.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.archival_item.view.*
import minipaige.example.provenance.com.Controller.ItemDetailActivity
import minipaige.example.provenance.com.Model.ArchivalItem
import minipaige.example.provenance.com.R
import minipaige.example.provenance.com.Utilities.EXTRA_ARCHVIAL_ITEM

class ArchivalItemsAdapter(val context: Context, val archivalItems: List<ArchivalItem>) : RecyclerView.Adapter<ArchivalItemsAdapter.MyViewHolder>(), Filterable {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.archival_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
       return archivalItems.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = archivalItems[position]
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

    override fun getFilter(): Filter {
        return filter
    }

    inner class RecycleFilter : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            var results : FilterResults = FilterResults()
            if (constraint != null && constraint.length > 0) {
                var localList: MutableList<ArchivalItem> = mutableListOf()
                for (i: Int in 0..archivalItems.size.minus(1) as Int) {
                    if (archivalItems[i].collection?.toLowerCase()?.contains(constraint.toString().toLowerCase()) as Boolean) {
                        localList.add(archivalItems[i])
                    }
                }
                println("Local list is: $localList")
                results.values = localList
                results.count = localList.size
            } else {
                results.values = archivalItems
                results.count = archivalItems.size
            }
            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
//            archivalItems = results?.values as ArrayList<ArchivalItem>
            println("inside publish results")
            notifyDataSetChanged()
        }

    }

//
//    inner class NewFilter(var adapter: ArchivalItemsAdapter) : Filter() {
//        override fun performFiltering(constraint: CharSequence?): FilterResults {
//            var archivalItemsQ
//            archivalItemsQ!!.clear()
//            val results = FilterResults()
//            if (constraint.lenth == 0) {
//                archivalItemsQ!!.addAll(archivalItemsList)
//            } else {
//                val filterPattern = constraint.toString().toLowerCase().trim()
//                for (item in archivalItems) {
//                    if (item.collection.toLowerCase().contains(filterPattern, true)){
//                        archivalItemsQ!!.add(item)
//                    }
//                }
//            }
//
//            results.values = archivalItemsQ
//            results.count = archivalItemsQ!!.size
//
//            return results
//        }
//
//        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
//            this.adapter.notifyDataSetChanged()
//        }
//    }
}