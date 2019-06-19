package com.darkkillen.archmvvm.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.darkkillen.archmvvm.db.converter.StringListConverter
import com.darkkillen.archmvvm.jni.JniHelper
import com.darkkillen.archmvvm.vo.Example

@Database(entities = [
    Example::class
], version = 1)

@TypeConverters(value = [
    StringListConverter::class
])
abstract class AppDatabase : RoomDatabase() {

    // Other dao here..

    companion object {

        private val DATABASE_FILENAME = JniHelper.databaseName()
        private var INSTANCE: AppDatabase? = null
        private val LOCK = Any()
        private val LOCK_INMEM = Any()

        fun getInMemory(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(LOCK_INMEM) {
                    INSTANCE = Room.inMemoryDatabaseBuilder(context.applicationContext, AppDatabase::class.java)
                            // To simplify the codelab, allow queries on the main thread.
                            // Don't do this on a real app! See PersistenceBasicSample for an example.
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build()
                }
            }
            return INSTANCE!!
        }

        fun getInDatabase(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(LOCK) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, DATABASE_FILENAME)
                            .addMigrations(object : Migration(2, 3) {
                                override fun migrate(supportSQLiteDatabase: SupportSQLiteDatabase) {

                                }
                            })
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build()
                }
            }

            return INSTANCE!!
        }

        fun create(context: Context, useInMemory: Boolean): AppDatabase {

            return if (useInMemory) getInMemory(context) else getInDatabase(context)
        }

        fun destroyInstance() {
            if (INSTANCE != null) INSTANCE!!.close()
            INSTANCE = null
        }
    }

}