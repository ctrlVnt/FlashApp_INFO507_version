import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.flashapp.R
import java.util.ArrayList

class GlobalAdapter(ctx: Context, private val dataModelArrayList: ArrayList<model.Collection>) :
    RecyclerView.Adapter<GlobalAdapter.GlobalViewHolder>() {

    inner class GlobalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var name: TextView
        var tag: TextView
        var number: TextView

        init {
            name = itemView.findViewById<View>(R.id.collection_name) as TextView
            tag = itemView.findViewById<View>(R.id.collection_tag) as TextView
            number = itemView.findViewById<View>(R.id.collection_number) as TextView
        }
    }

    private val inflater: LayoutInflater

    init {
        inflater = LayoutInflater.from(ctx)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GlobalViewHolder {
        val view = inflater.inflate(R.layout.item_collection, parent, false)
        return GlobalViewHolder(view)
    }

    override fun onBindViewHolder(holder: GlobalViewHolder, position: Int) {
        holder.name.setText(dataModelArrayList[position].name)
        holder.tag.setText(dataModelArrayList[position].tag)
        holder.number.setText(dataModelArrayList[position].card_number)
    }

    override fun getItemCount(): Int {
        return dataModelArrayList.size
    }
}