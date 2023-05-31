package com.example.pit_a_pet

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MealManagementDao {
    @Insert
    fun insert(mealManagement: MealManagement)

    @Query("SELECT * FROM MealManagementTable") // 테이블의 모든 값을 가져와라
    fun getMealManagement(): List<MealManagement>
}
@Dao
interface DangerFoodDao {
    @Insert
    fun insert(dangerFood: DangerFood)

    @Query("SELECT * FROM DangerFoodTable") // 테이블의 모든 값을 가져와라
    fun getDangerFood(): List<DangerFood>
}