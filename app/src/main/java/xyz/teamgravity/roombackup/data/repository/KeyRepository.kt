package xyz.teamgravity.roombackup.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import xyz.teamgravity.roombackup.data.local.dao.KeyDao
import xyz.teamgravity.roombackup.data.mapper.toEntity
import xyz.teamgravity.roombackup.data.mapper.toModel
import xyz.teamgravity.roombackup.data.model.KeyModel

class KeyRepository(
    private val dao: KeyDao,
) {

    ///////////////////////////////////////////////////////////////////////////
    // INSERT
    ///////////////////////////////////////////////////////////////////////////

    suspend fun insertKey(key: KeyModel) {
        dao.insertKey(key.toEntity())
    }

    ///////////////////////////////////////////////////////////////////////////
    // DELETE
    ///////////////////////////////////////////////////////////////////////////

    suspend fun deleteLatestKey() {
        dao.deleteLatestKey()
    }

    ///////////////////////////////////////////////////////////////////////////
    // GET
    ///////////////////////////////////////////////////////////////////////////

    fun getAllKeys(): Flow<List<KeyModel>> {
        return dao.getAllKeys().map { entities -> entities.map { it.toModel() } }
    }
}