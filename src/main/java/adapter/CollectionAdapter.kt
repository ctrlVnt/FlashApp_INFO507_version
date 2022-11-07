package adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.flashapp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CollectionAdapter(val c: Context, val collList:ArrayList<model.Collection>, private var nameItem: String ):RecyclerView.Adapter<CollectionAdapter.CollectionViewHolder>() {

    private lateinit var mlistener : onItemClickListener

    interface onItemClickListener{
        fun onItemClick(nameItem: String, tagItem: String)
        fun onAddClick(position: Int)
    }

    fun setonItemClickListener(listener : onItemClickListener){
        mlistener = listener
    }

    inner class CollectionViewHolder(val v:View, listener : onItemClickListener, nameItem:String):RecyclerView.ViewHolder(v){
        var nameCollection:TextView
        var tagCollection:TextView
        var numberCard:TextView

        init {
            nameCollection = v.findViewById(R.id.collection_name)
            tagCollection = v.findViewById(R.id.collection_tag)
            numberCard = v.findViewById(R.id.collection_number)
            v.setOnClickListener(){
                mlistener.onItemClick(nameCollection.text as String, tagCollection.text as String)
            }
            if (nameItem == "collection") {
                v.findViewById<FloatingActionButton>(R.id.delete_collection).setOnClickListener {
                    //removeItem(adapterPosition)
                    mlistener.onAddClick(adapterPosition)
                }
            }else{
                v.findViewById<FloatingActionButton>(R.id.delete_collection).visibility = View.INVISIBLE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v  = inflater.inflate(R.layout.item_collection,parent,false)
        return CollectionViewHolder(v, mlistener, nameItem)
    }

    override fun onBindViewHolder(holder: CollectionViewHolder, position: Int) {
        val newList = collList[position]
        holder.nameCollection.text = newList.name
        holder.tagCollection.text = "#"+newList.tag
        holder.numberCard.text = "NOMBRE DES CARTES: " + newList.card_number.toString()
    }

    override fun getItemCount(): Int {
        return  collList.size
    }

    private fun removeItem(position: Int){
        collList.removeAt(position)
        notifyItemRemoved(position)
    }
}