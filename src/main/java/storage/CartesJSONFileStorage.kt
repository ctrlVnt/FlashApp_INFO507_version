package storage

import android.content.Context
import model.Card
import org.json.JSONObject
import storage.utility.file.JSONFileStorage

class CartesJSONFileStorage(context: Context): JSONFileStorage<Card>(context,"cartes") {
    override fun create(id: Int, obj: Card): Card {
        return Card(obj.name,obj.tag,obj.card_number)
    }

    override fun objectToJson(id: Int, obj: Card): JSONObject {
        var res = JSONObject()
        res.put(Card.NAME,obj.name)
        res.put(Card.TAG,obj.tag)
        res.put(Card.CARDNUMBER,obj.card_number)
        return res
    }

    override fun jsonToObject(json: JSONObject): Card {
        return Card(
            json.getString(Card.NAME),
            json.getString(Card.TAG),
            json.getInt(Card.CARDNUMBER),
            )
    }
}