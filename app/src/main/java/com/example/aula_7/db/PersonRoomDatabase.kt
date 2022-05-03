package com.example.aula_7.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.aula_7.dao.PersonDao
import com.example.aula_7.model.Person
import android.content.Context
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Database(entities = [Person::class], version = 1, exportSchema = false)
abstract class PersonRoomDatabase: RoomDatabase() {

  abstract fun personDao(): PersonDao

  private class PersonDatabaseCallback(
    private val scope: CoroutineScope
  ) : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
      super.onCreate(db)
      INSTANCE?.let { database ->
        scope.launch {
          populateDatabase(database.personDao())
        }
      }
    }

    suspend fun populateDatabase(personDao: PersonDao) {
      // Delete all content here.
      personDao.deleteAll()

      // Add sample words.
      var person = Person("Hello")
      personDao.insert(person)
      person = Person("World!")
      personDao.insert(person)

      // TODO: Add your own words!
    }
  }

  companion object {

    @Volatile
    private var INSTANCE: PersonRoomDatabase? = null

    fun getDatabase(context: Context, scope: CoroutineScope): PersonRoomDatabase {
      return INSTANCE ?: synchronized(this) {
        val instance = Room.databaseBuilder(
          context.applicationContext,
          PersonRoomDatabase::class.java,
          "person_database"
        )
          .addCallback(PersonDatabaseCallback(scope))
          .build()
        INSTANCE = instance
        instance
      }
    }
  }
}

