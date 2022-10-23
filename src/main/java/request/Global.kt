package request
import android.content.Context
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.example.flashapp.R


class Global (private val context : Context){
    /*init {
        val request = JsonObjectRequest(
            Request.Method.GET,
            "http://51.68.95.247/gr-1-8/collection.json",
            null,
            { res -> Toast.makeText(context, R.string.success, Toast.LENGTH_SHORT).show()
                val jsonArray = res.getJSONArray("collection")
                for(i in 0 .. jsonArray.length()){
                    val json = jsonArray.getJSONObject(i)
                    val collection = model.Collection(
                        json.getString(model.Collection.NAME),
                        json.getString(model.Collection.TAG),
                        json.getInt(model.Collection.CARDNUMBER)
                    )
                }
            }
            CollectionStorage.setStorage(context),
            { err -> Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT).show() }
        )
    }*/
}