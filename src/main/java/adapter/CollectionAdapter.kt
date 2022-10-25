package adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.flashapp.R

/*class CollectionAdapter (val c: Context, val countries: List<model.Collection>): RecyclerView.Adapter<CollectionAdapter.CountriesViewHolder>() {

    class CountriesViewHolder(itemView : View, listener : CollectionAdapter.onItemClickListener): RecyclerView.ViewHolder(itemView){
        private val officialName: TextView = itemView.findViewById(R.id.collect_name)
        private val independent: TextView = itemView.findViewById(R.id.collection_tag)
        private val capital: TextView = itemView.findViewById(R.id.collection_number)

        init {
            itemView.setOnClickListener(){
                listener.onItemClick(adapterPosition)
            }
        }


        fun bind(collection: model.Collection) {

            //Integer.parseInt

            //  val sum = (user.id + user.id).toString()
            officialName.text = collection.name //user.address.geo.lat + " / " + user.address.geo.lng
            independent.text = collection.tag
            capital.text = collection.card_number.toString()


        }

    }

    private lateinit var mlistener : onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setonItemClickListener(listener : onItemClickListener){
        mlistener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountriesViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_collection, parent, false)
        return CountriesViewHolder(view, mlistener)
    }

    override fun getItemCount(): Int {
        return countries.size
    }

    override fun onBindViewHolder(holder: CountriesViewHolder, position: Int) {
        return holder.bind(countries[position])

    }
}*/


class CollectionAdapter(val c: Context, val collList:ArrayList<model.Collection>):RecyclerView.Adapter<CollectionAdapter.CollectionViewHolder>() {

    private lateinit var mlistener : onItemClickListener

    interface onItemClickListener{
        fun onItemClick(nameItem: String)
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
                listener.onItemClick(nameCollection.text as String)
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