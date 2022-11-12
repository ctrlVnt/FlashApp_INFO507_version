package adapter

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.flashapp.CollectionActivity
import com.example.flashapp.R
import model.Cartes

class CartesAdapter(val c: Context, val collList:ArrayList<Cartes>, private var glob:Int):RecyclerView.Adapter<CartesAdapter.CartesViewHolder>() {

    private lateinit var mlistener : CartesAdapter.onItemClickListener

    interface onItemClickListener{
        fun onItemClick(questionItem: String, reponseItem: String, position: Int)
        fun deleteCardClick(position: Int)
    }

    fun setonItemClickListener(listener : onItemClickListener){
        mlistener = listener
    }

    inner class CartesViewHolder(val v:View, listener : CartesAdapter.onItemClickListener,global:Int ):RecyclerView.ViewHolder(v) {

        var question: TextView
        var reponse: TextView
        val qimagine: ImageView

        init {
            question = v.findViewById(R.id.card_question)
            reponse = v.findViewById(R.id.card_response)
            qimagine = v.findViewById(R.id.imagine_question)
            if (global == 1) {
                v.findViewById<ImageButton>(R.id.cart_delete).visibility = View.INVISIBLE
            }else {
                v.setOnClickListener() {
                    mlistener.onItemClick(
                        question.text as String,
                        reponse.text as String,
                        adapterPosition
                    )
                }
                v.findViewById<ImageButton>(R.id.cart_delete).setOnClickListener {
                    mlistener.deleteCardClick(adapterPosition)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int, ): CartesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v  = inflater.inflate(R.layout.item_card,parent,false)
        return CartesViewHolder(v, mlistener, glob)
    }

    override fun onBindViewHolder(holder: CartesViewHolder, position: Int) {
        val newList = collList[position]
        holder.question.text = newList.question
        holder.reponse.text = newList.reponse
        if (newList.image != Uri.EMPTY.toString()) {
            val selectedImage: Uri = Uri.parse(newList.image)
            holder.qimagine.setImageURI(selectedImage)
        }
        else{
            holder.qimagine.setImageURI(Uri.EMPTY)
        }
    }

    override fun getItemCount(): Int {
        return  collList.size
    }
}