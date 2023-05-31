package com.example.pit_a_pet

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MealManagement::class, DangerFood::class], version = 2)
abstract class PetKnowledgeDatabase: RoomDatabase() {
    abstract fun mealManagementDao(): MealManagementDao
    abstract fun dangerFoodDao(): DangerFoodDao
    companion object {
        private var instance: PetKnowledgeDatabase? = null

        @Synchronized
        fun getInstance(context: Context): PetKnowledgeDatabase? {
            if (instance == null) {
                synchronized(PetKnowledgeDatabase::class){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        PetKnowledgeDatabase::class.java,
                        "pet-knowledge-database"//다른 데이터 베이스랑 이름겹치면 꼬임
                    ).allowMainThreadQueries().build()
                }
            }

            return instance
        }
    }
}