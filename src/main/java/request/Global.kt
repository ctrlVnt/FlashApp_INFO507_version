package request
import adapter.GlobalAdapter
import android.content.Context
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.flashapp.R
import model.Collection
import storage.CollectionStorage
import storage.utility.Storage


class Global (private val context : Context){

    /*private var globalAdapter: GlobalAdapter
    private var globalList: ArrayList<Storage<Collection>>
    private lateinit var recvGlobale: RecyclerView
    private lateinit var element : Storage<Collection>*/

    init {
        /*globalList = ArrayList()
        recvGlobale.layoutManager = LinearLayoutManager(context)
        globalAdapter = GlobalAdapter(globalList)
        recvGlobale.adapter = globalAdapter*/
        val queue = Volley.newRequestQueue(context)
        val request = JsonObjectRequest(
            Request.Method.GET,
            "http://51.68.95.247/gr-1-8/collection.json",
            null,
            { res ->
                val jsonArray = res.getJSONArray("collection")
                for (i in 0 until jsonArray.length()) {
                    val collect = jsonArray.getJSONObject(i)
                    CollectionStorage.get(context).insert(
                        Collection(
                            collect.getInt(Collection.ID),
                            collect.getString(Collection.NAME),
                            collect.getString(Collection.TAG),
                            collect.getInt(Collection.CARDNUMBER)
                        )
                    )

                    Toast.makeText(context, collect.getString(Collection.NAME), Toast.LENGTH_SHORT).show()
                }
                //globalList.add(element)
                Toast.makeText(context, R.string.request_success, Toast.LENGTH_SHORT).show()
            },
            { err -> Toast.makeText(context, R.string.request_fail, Toast.LENGTH_SHORT).show() }
        )
        queue.add(request)
        queue.start()
    }
}