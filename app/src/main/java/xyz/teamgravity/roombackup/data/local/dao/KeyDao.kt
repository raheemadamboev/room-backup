package xyz.teamgravity.roombackup.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import xyz.teamgravity.roombackup.data.local.constant.KeyConst.TABLE_KEY
import xyz.teamgravity.roombackup.data.local.entity.KeyEntity

@Dao
interface KeyDao {

    ///////////////////////////////////////////////////////////////////////////
    // INSERT
    ///////////////////////////////////////////////////////////////////////////

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKey(key: KeyEntity)

    ///////////////////////////////////////////////////////////////////////////
    // DELETE
    ///////////////////////////////////////////////////////////////////////////

    @Query("DELETE FROM $TABLE_KEY ORDER BY id DESC LIMIT 1")
    suspend fun deleteLatestKey()

    ///////////////////////////////////////////////////////////////////////////
    // GET
    ///////////////////////////////////////////////////////////////////////////

    @Query("SELECT * FROM $TABLE_KEY ORDER BY id DESC")
    fun getAllKeys(): Flow<List<KeyEntity>>
}