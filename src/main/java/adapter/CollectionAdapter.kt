package adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.flashapp.R

class CollectionAdapter(private val collections: List<model.Collection>): RecyclerView.Adapter<CollectionAdapter.CollectionviewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionviewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_collection, parent, false)
        return CollectionviewHolder(view)
    }

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

    override fun getItemCount(): Int {
        return collections.size
    }

    override fun onBindViewHolder(holder: CollectionviewHolder, position: Int) {
        return holder.bind(collections[position])
    }
}