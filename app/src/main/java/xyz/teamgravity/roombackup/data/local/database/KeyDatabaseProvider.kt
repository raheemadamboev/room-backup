package xyz.teamgravity.roombackup.data.local.database

import android.app.Application
import androidx.room.Room
import xyz.teamgravity.roombackup.data.local.constant.KeyConst
import xyz.teamgravity.roombackup.data.local.dao.KeyDao

class KeyDatabaseProvider(
    private val application: Application,
) {

    private var database: KeyDatabase? = null

    @Synchronized
    fun instance(): KeyDatabase {
        if (database == null) {
            database = Room.databaseBuilder(application, KeyDatabase::class.java, KeyConst.NAME)
                .fallbackToDestructiveMigration()
                .build()
        }

        return database!!
    }

    @Synchronized
    fun close() {
        database?.close()
        database = null
    }

    fun keyDao(): KeyDao {
        return instance().keyDao()
    }
}