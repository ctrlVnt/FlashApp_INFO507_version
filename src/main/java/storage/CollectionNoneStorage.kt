package storage

import model.Collection
import storage.utility.Storage

class CollectionNoneStorage: Storage<Collection>(){
    override fun insert(obj: Collection): Int {
        TODO("Not yet implemented")
    }

    override fun find(id: Int): Collection? {
        TODO("Not yet implemented")
    }

    override fun size(): Int {
        TODO("Not yet implemented")
    }

    override fun findAll(): List<Collection> {
        TODO("Not yet implemented")
    }

    override fun update(id: Int, obj: Collection) {
        TODO("Not yet implemented")
    }

    override fun delete(id: Int) {
        TODO("Not yet implemented")
    }

}