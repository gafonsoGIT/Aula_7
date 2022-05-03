package com.example.aula_7

import android.app.Application
import com.example.aula_7.db.PersonRoomDatabase
import com.example.aula_7.repository.PersonRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class PersonApplication : Application() {

  val applicationScope = CoroutineScope(SupervisorJob())

  val database by lazy { PersonRoomDatabase.getDatabase(this, applicationScope) }
  val repository by lazy { PersonRepository(database.personDao()) }
}
