package xyz.teamgravity.roombackup.data.repository

import android.content.Context
import android.net.Uri
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExecutorCoroutineDispatcher
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import xyz.teamgravity.roombackup.data.local.constant.KeyConst
import xyz.teamgravity.roombackup.data.local.database.KeyDatabaseProvider

class BackupRepository(
    private val provider: KeyDatabaseProvider,
    private val context: Context,
    private val mutex: Mutex,
    private val scope: CoroutineScope,
    private val dispatcher: ExecutorCoroutineDispatcher,
) {

    suspend fun export(uri: Uri) {
        withContext(dispatcher + scope.coroutineContext) {
            mutex.withLock {
                provider.close()

                context.contentResolver.openOutputStream(uri)?.use { stream ->
                    context.getDatabasePath(KeyConst.NAME).inputStream().copyTo(stream)
                }
            }
        }
    }

    suspend fun import(uri: Uri) {
        withContext(dispatcher + scope.coroutineContext) {
            mutex.withLock {
                provider.close()

                context.contentResolver.openInputStream(uri)?.use { stream ->
                    val dbFile = context.getDatabasePath(KeyConst.NAME)
                    dbFile?.delete()
                    stream.copyTo(dbFile.outputStream())
                }
            }
        }
    }
}