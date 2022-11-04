package adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.flashapp.R
import model.Cartes

class CartesAdapter(val c: Context, val collList:ArrayList<Cartes>):RecyclerView.Adapter<CartesAdapter.CartesViewHolder>() {

    private lateinit var mlistener : CartesAdapter.onItemClickListener

    interface onItemClickListener{
        fun onItemClick(questionItem: String, reponseItem: String)
    }

    fun setonItemClickListener(listener : onItemClickListener){
        mlistener = listener
    }

    inner class CartesViewHolder(val v:View, listener : CartesAdapter.onItemClickListener):RecyclerView.ViewHolder(v){
        var question:TextView
        var reponse:TextView

        init {
            question = v.findViewById(R.id.card_question)
            reponse = v.findViewById(R.id.card_response)

            v.setOnClickListener(){
                mlistener.onItemClick(question.text as String, reponse.text as String)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v  = inflater.inflate(R.layout.item_card,parent,false)
        return CartesViewHolder(v, mlistener)
    }

    override fun onBindViewHolder(holder: CartesViewHolder, position: Int) {
        val newList = collList[position]
        holder.question.text = newList.question
        holder.reponse.text = newList.reponse
    }

    override fun getItemCount(): Int {
        return  collList.size
    }
}