package com.example.starwarsentitymanagementsystem.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.starwarsentitymanagementsystem.data.dao.SpeciesDao
import com.example.starwarsentitymanagementsystem.data.model.Species
import kotlinx.coroutines.runBlocking
import java.util.concurrent.Executors

@Database(
    version = 1,
    entities = [Species::class],
    exportSchema = false
)
abstract class StarWarsDatabase : RoomDatabase() {

    abstract fun getSpeciesDao(): SpeciesDao

    companion object {
        @Volatile
        private var INSTANCE: StarWarsDatabase? = null

        fun getDatabase(context: Context): StarWarsDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StarWarsDatabase::class.java,
                    "starwars_species_db"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            Executors.newSingleThreadExecutor().execute {
                                INSTANCE?.let { prepopulateDatabase(it) }
                            }
                        }
                    })
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private fun prepopulateDatabase(database: StarWarsDatabase) {
            val dao = database.getSpeciesDao()
            runBlocking {
                // Ajustado para tener description Y discoveryDate
                dao.insertSpecie(Species(1, "Wookiee", "Mammal", "Sentient", "Shyriiwook", "Peludos y altos", "1977-05-25", false))
                dao.insertSpecie(Species(2, "Hutt", "Gastropod", "Sentient", "Huttese", "Grandes y babosos", "1983-05-25", false))
                dao.insertSpecie(Species(3, "Droid", "Artificial", "Sentient", "Binary", "Metalicos", "1977-05-25", true))
            }
        }
    }
}