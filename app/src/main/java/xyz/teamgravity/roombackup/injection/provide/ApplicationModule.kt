package xyz.teamgravity.roombackup.injection.provide

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExecutorCoroutineDispatcher
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.sync.Mutex
import xyz.teamgravity.roombackup.data.local.database.KeyDatabaseProvider
import xyz.teamgravity.roombackup.data.repository.BackupRepository
import xyz.teamgravity.roombackup.data.repository.KeyRepository
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    @Singleton
    fun provideKeyDatabaseProvider(application: Application): KeyDatabaseProvider = KeyDatabaseProvider(application)

    @Provides
    @Singleton
    fun provideCoroutineScope(): CoroutineScope = CoroutineScope(SupervisorJob())

    @Provides
    fun provideMutex(): Mutex = Mutex()

    @Provides
    @Singleton
    fun provideExecutorCoroutineDispatcher(): ExecutorCoroutineDispatcher = Executors.newSingleThreadExecutor().asCoroutineDispatcher()

    @Provides
    @Singleton
    fun provideKeyRepository(keyDatabaseProvider: KeyDatabaseProvider): KeyRepository = KeyRepository(keyDatabaseProvider)

    @Provides
    @Singleton
    fun provideBackupRepository(
        keyDatabaseProvider: KeyDatabaseProvider,
        application: Application,
        mutex: Mutex,
        coroutineScope: CoroutineScope,
        executorCoroutineDispatcher: ExecutorCoroutineDispatcher,
    ): BackupRepository = BackupRepository(
        provider = keyDatabaseProvider,
        context = application,
        mutex = mutex,
        scope = coroutineScope,
        dispatcher = executorCoroutineDispatcher
    )
}