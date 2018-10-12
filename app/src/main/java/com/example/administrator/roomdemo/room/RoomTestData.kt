package com.example.administrator.roomdemo.room

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.migration.Migration
import android.content.Context


/**
 * @author  : Alex
 * @date    : 2018/08/07
 * @version : V 2.0.0
 */
@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class RoomTestData : RoomDatabase() {
    public abstract fun userDao(): UserDao

    companion object {

        @Volatile
        private var INSTANCE: RoomTestData? = null
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE book (id  INTEGER , name TEXT )")
            }
        }
        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE user ADD COLUMN strength INTEGER NOT NUll DEFAULT 0")
            }
        }

        fun getInstance(context: Context): RoomTestData =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
                }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext,
                        RoomTestData::class.java, "Sample.db")
                        .addMigrations(MIGRATION_1_2)
                        .addMigrations(MIGRATION_2_3)
                        .build()
    }
}