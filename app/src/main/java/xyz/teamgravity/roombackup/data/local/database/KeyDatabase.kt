package xyz.teamgravity.roombackup.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import xyz.teamgravity.roombackup.data.local.constant.KeyConst
import xyz.teamgravity.roombackup.data.local.dao.KeyDao
import xyz.teamgravity.roombackup.data.local.entity.KeyEntity

@Database(
    entities = [KeyEntity::class],
    version = KeyConst.VERSION,
    exportSchema = false
)
abstract class KeyDatabase : RoomDatabase() {

    abstract fun keyDao(): KeyDao
}