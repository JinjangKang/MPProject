package com.example.pit_a_pet

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SongDao {
    @Insert
    fun insert(song: Song)

    @Query("SELECT * FROM SongTable") // 테이블의 모든 값을 가져와라
    fun getSongs(): List<Song>
}