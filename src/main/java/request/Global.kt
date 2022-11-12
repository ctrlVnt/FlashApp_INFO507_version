package request

import android.content.Context
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.flashapp.R
import model.Cartes
import model.Collection
import storage.CartesStorage
import storage.CollectionStorage


class Global (private val context : Context, tot:Int){

    init {
        initCollection(tot)
    }

    private fun initCollection(tot:Int){
        val queue = Volley.newRequestQueue(context)
        val request = JsonObjectRequest(
            Request.Method.GET,
            "http://51.68.95.247/gr-1-8/collection.json",
            null,
            { res ->
                val jsonArray = res.getJSONArray("collection")
                if(tot < jsonArray.length()){
                    for (i in tot until jsonArray.length()) {
                        val collect = jsonArray.getJSONObject(i)
                        CollectionStorage.get(context, "global").insert(
                            Collection(
                                collect.getInt(Collection.ID),
                                collect.getString(Collection.NAME),
                                collect.getString(Collection.TAG),
                                collect.getInt(Collection.CARDNUMBER)
                            )
                        )
                    }
                    initCartes()
                    Toast.makeText(context, R.string.request_success, Toast.LENGTH_SHORT).show()
                }
            },
            { err -> Toast.makeText(context, R.string.request_fail, Toast.LENGTH_SHORT).show() }
        )
        queue.add(request)
        queue.start()
    }

    private fun initCartes(){
        val queue = Volley.newRequestQueue(context)
        val request = JsonObjectRequest(
            Request.Method.GET,
            "http://51.68.95.247/gr-1-8/cartes.json",
            null,
            { res ->
                val jsonArray = res.getJSONArray("cartes")
                    for (i in 1 until jsonArray.length()) {
                        val cart = jsonArray.getJSONObject(i)
                        CartesStorage.get(context, cart.getString(Cartes.COLLECTION)).insert(
                            Cartes(
                                cart.getInt(Cartes.ID),
                                cart.getString(Cartes.COLLECTION),
                                cart.getString(Cartes.QUESTION),
                                cart.getString(Cartes.REPONSE),
                                cart.getString(Cartes.IMAGE)
                            )
                        )
                    }
            },
            { err -> Toast.makeText(context, R.string.request_fail, Toast.LENGTH_SHORT).show() }
        )
        queue.add(request)
        queue.start()
    }

    private fun delete(){
        for (col in CollectionStorage.get(context, "global").findAll()) {
            CollectionStorage.get(context, "global").delete(col.id)
        }
    }

    fun writeOnGlobal(collection: Collection) {
        /*method to implement*/
    }
}