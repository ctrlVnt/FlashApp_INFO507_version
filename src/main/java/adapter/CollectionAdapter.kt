package adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.example.flashapp.R

class CollectionAdapter: RecyclerView.Adapter<CollectionAdapter.ExpenseHolder>(){

    class ExpenseHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var name: EditText = itemView.findViewById(R.id.collect_name)
        var tag: EditText = itemView.findViewById(R.id.tag_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.collection_element, parent, false)
        return ExpenseHolder(view)
    }

    override fun onBindViewHolder(holder: ExpenseHolder, position: Int) {
        holder.name.setText(toString())
        holder.tag.setText(toString())
    }

    override fun getItemCount(): Int {
        return 10
    }
}