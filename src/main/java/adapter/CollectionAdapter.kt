package adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.flashapp.R

class CollectionAdapter(val c: Context, val collList:ArrayList<model.Collection>):RecyclerView.Adapter<CollectionAdapter.CollectionViewHolder>() {

    private lateinit var mlistener : onItemClickListener

    interface onItemClickListener{
        fun onItemClick(nameItem: String, tagItem: String)
    }

    fun setonItemClickListener(listener : onItemClickListener){
        mlistener = listener
    }

    inner class CollectionViewHolder(val v:View, listener : onItemClickListener):RecyclerView.ViewHolder(v){
        var nameCollection:TextView
        var tagCollection:TextView

        init {
            nameCollection = v.findViewById<TextView>(R.id.collection_name)
            tagCollection = v.findViewById<TextView>(R.id.collection_tag)
            v.setOnClickListener(){
                listener.onItemClick(nameCollection.text as String, tagCollection.text as String)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v  = inflater.inflate(R.layout.item_collection,parent,false)
        return CollectionViewHolder(v, mlistener)
    }

    override fun onBindViewHolder(holder: CollectionViewHolder, position: Int) {
        val newList = collList[position]
        holder.nameCollection.text = newList.name
        holder.tagCollection.text = newList.tag
    }

    override fun getItemCount(): Int {
        return  collList.size
    }
}