package adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.flashapp.R
import storage.CollectionStorage

/*class CollectionAdapter(private val collections: List<model.Collection>): RecyclerView.Adapter<CollectionAdapter.CollectionviewHolder>() {

    class CollectionviewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val officialName: TextView = itemView.findViewById(R.id.collection_name)
        private val independent: TextView = itemView.findViewById(R.id.collection_tag)
        private val capital: TextView = itemView.findViewById(R.id.collection_number)

        fun bind(collection: model.Collection) {
            officialName.text = collection.name
            independent.text = collection.tag
            capital.text = collection.card_number.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionviewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_collection, parent, false)
        return CollectionviewHolder(view)
    }

    override fun getItemCount(): Int {
        return collections.size
    }

    override fun onBindViewHolder(holder: CollectionviewHolder, position: Int) {
        return holder.bind(collections[position])
    }
}*/

class CollectionAdapter(val c: Context, val collList:ArrayList<model.Collection>):RecyclerView.Adapter<CollectionAdapter.CollectionViewHolder>() {

    /*abstract fun onItemClick(view: View)
    abstract fun onLongItemClick(view: View): Boolean*/

    private lateinit var mlistener : onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setonItemClickListener(listener : onItemClickListener){
        mlistener = listener
    }

    inner class CollectionViewHolder(val v:View, listener : onItemClickListener):RecyclerView.ViewHolder(v){
        var nameCollection:TextView
        var tagCollection:TextView
        //var img: ImageView

        init {
            nameCollection = v.findViewById<TextView>(R.id.collection_name)
            tagCollection = v.findViewById<TextView>(R.id.collection_tag)
            /*img = v.findViewById(R.id.collection_number)
            img.setOnClickListener { popupMenus(it) }*/
            v.setOnClickListener(){
                listener.onItemClick(adapterPosition)
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
        /*holder.itemView.tag = model.Collection.NAME*/
        holder.nameCollection.text = newList.name
        holder.tagCollection.text = newList.tag
    }

    override fun getItemCount(): Int {
        return  collList.size
    }
}