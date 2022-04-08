package com.example.aula_7.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.aula_7.dao.PersonDao
import com.example.aula_7.model.Person
import android.content.Context
@Database(entities = [Person::class], version = 1, exportSchema = false)
abstract class PersonRoomDatabase: RoomDatabase() {

  abstract fun personDao(): PersonDao

  companion object {

    @Volatile
    private var INSTANCE: PersonRoomDatabase? = null

    fun getDatabase(context: Context): PersonRoomDatabase {
      return INSTANCE ?: synchronized(this) {
        val instance = Room.databaseBuilder(
          context.applicationContext,
          PersonRoomDatabase::class.java,
          "person_database"
        ).build()
        INSTANCE = instance
        instance
      }
    }
  }
}
