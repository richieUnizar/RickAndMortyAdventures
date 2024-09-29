package com.example.data_source_room.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data_source_room.dao.CharacterDao

@Database(entities = [CharacterEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
}