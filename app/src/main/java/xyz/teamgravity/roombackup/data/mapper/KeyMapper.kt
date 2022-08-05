package xyz.teamgravity.roombackup.data.mapper

import xyz.teamgravity.roombackup.data.local.entity.KeyEntity
import xyz.teamgravity.roombackup.data.model.KeyModel

fun KeyEntity.toModel(): KeyModel {
    return KeyModel(
        id = id,
        key = key
    )
}

fun KeyModel.toEntity(): KeyEntity {
    return KeyEntity(
        id = id,
        key = key
    )
}