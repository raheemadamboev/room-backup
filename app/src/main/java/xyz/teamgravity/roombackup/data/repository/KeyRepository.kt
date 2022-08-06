package xyz.teamgravity.roombackup.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import xyz.teamgravity.roombackup.data.local.database.KeyDatabaseProvider
import xyz.teamgravity.roombackup.data.mapper.toEntity
import xyz.teamgravity.roombackup.data.mapper.toModel
import xyz.teamgravity.roombackup.data.model.KeyModel

class KeyRepository(
    private val provider: KeyDatabaseProvider,
) {

    ///////////////////////////////////////////////////////////////////////////
    // INSERT
    ///////////////////////////////////////////////////////////////////////////

    suspend fun insertKey(key: KeyModel) {
        provider.keyDao().insertKey(key.toEntity())
    }

    ///////////////////////////////////////////////////////////////////////////
    // DELETE
    ///////////////////////////////////////////////////////////////////////////

    suspend fun deleteLatestKey() {
        provider.keyDao().deleteLatestKey()
    }

    ///////////////////////////////////////////////////////////////////////////
    // GET
    ///////////////////////////////////////////////////////////////////////////

    fun getAllKeys(): Flow<List<KeyModel>> {
        return provider.keyDao().getAllKeys().map { entities -> entities.map { it.toModel() } }
    }
}