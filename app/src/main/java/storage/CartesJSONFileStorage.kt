package storage

import android.content.Context
import model.Cartes
import org.json.JSONObject
import storage.utility.file.JSONFileStorage

class CartesJSONFileStorage (context: Context, collection:String): JSONFileStorage<Cartes>(context,collection) {
    override fun create(id: Int, obj: Cartes): Cartes {
        return Cartes(obj.id, obj.collection,obj.question,obj.reponse, obj.image)
    }

    override fun objectToJson(id: Int, obj: Cartes): JSONObject {
        var res = JSONObject()
        res.put(Cartes.ID,obj.id)
        res.put(Cartes.COLLECTION, obj.collection)
        res.put(Cartes.QUESTION,obj.question)
        res.put(Cartes.REPONSE,obj.reponse)
        res.put(Cartes.IMAGE, obj.image)
        return res
    }

    override fun jsonToObject(json: JSONObject): Cartes {
        return Cartes(
            json.getInt(Cartes.ID),
            json.getString(Cartes.COLLECTION),
            json.getString(Cartes.QUESTION),
            json.getString(Cartes.REPONSE),
            json.getString(Cartes.IMAGE)
            )
    }
}