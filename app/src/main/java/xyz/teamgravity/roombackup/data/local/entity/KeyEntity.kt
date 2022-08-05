package xyz.teamgravity.roombackup.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import xyz.teamgravity.roombackup.data.local.constant.KeyConst

@Entity(tableName = KeyConst.TABLE_KEY)
data class KeyEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long,

    val key: String,
)
