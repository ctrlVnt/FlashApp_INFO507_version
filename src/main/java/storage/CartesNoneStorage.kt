package storage

import model.Cartes
import storage.utility.Storage

class CartesNoneStorage: Storage<Cartes>() {
    override fun insert(obj: Cartes): Int {
        TODO("Not yet implemented")
    }

    override fun find(id: Int): Cartes? {
        TODO("Not yet implemented")
    }

    override fun size(): Int {
        TODO("Not yet implemented")
    }

    override fun findAll(): List<Cartes> {
        TODO("Not yet implemented")
    }

    override fun update(id: Int, obj: Cartes) {
        TODO("Not yet implemented")
    }

    override fun delete(id: Int) {
        TODO("Not yet implemented")
    }
}