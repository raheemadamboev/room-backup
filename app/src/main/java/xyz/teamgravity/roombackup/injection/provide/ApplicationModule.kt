package xyz.teamgravity.roombackup.injection.provide

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import xyz.teamgravity.roombackup.data.local.constant.KeyConst
import xyz.teamgravity.roombackup.data.local.dao.KeyDao
import xyz.teamgravity.roombackup.data.local.database.KeyDatabase
import xyz.teamgravity.roombackup.data.repository.KeyRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    @Singleton
    fun provideKeyDatabase(application: Application): KeyDatabase =
        Room.databaseBuilder(application, KeyDatabase::class.java, KeyConst.NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideKeyDao(keyDatabase: KeyDatabase): KeyDao = keyDatabase.keyDao()

    @Provides
    @Singleton
    fun provideKeyRepository(keyDao: KeyDao): KeyRepository = KeyRepository(keyDao)
}