package adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.flashapp.R
import storage.utility.Storage
import java.util.ArrayList

class GlobalAdapter(val dataModelArrayList:ArrayList<Storage<model.Collection>>):RecyclerView.Adapter<GlobalAdapter.GlobalViewHolder>() {

    private lateinit var mlistener : onItemClickListener

    interface onItemClickListener{
        fun onItemClick(nameItem: String, tagItem: String)
    }

    fun setonItemClickListener(listener : onItemClickListener){
        mlistener = listener
    }

    inner class GlobalViewHolder(itemView: View, listener : GlobalAdapter.onItemClickListener) : RecyclerView.ViewHolder(itemView) {

        var name: TextView
        var tag: TextView
        //var number: TextView

        init {
            name = itemView.findViewById<View>(R.id.collection_name) as TextView
            tag = itemView.findViewById<View>(R.id.collection_tag) as TextView
            //number = itemView.findViewById<View>(R.id.collection_number) as TextView

            itemView.setOnClickListener(){
                listener.onItemClick(name as String, tag.text as String)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GlobalViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_collection, parent, false)
        return GlobalViewHolder(view, mlistener)
    }

    override fun onBindViewHolder(holder: GlobalViewHolder, position: Int) {
        holder.name.text = dataModelArrayList[position].find(position)?.name
        holder.tag.text = dataModelArrayList[position].find(position)?.tag
        //holder.number.setText(dataModelArrayList[position].card_number)
    }

    override fun getItemCount(): Int {
        return dataModelArrayList.size
    }
}