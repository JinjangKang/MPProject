package com.example.pit_a_pet

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.pit_a_pet.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.*
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val initialFragment = HomeFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainFragment, initialFragment)
            .commit()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navigationBar = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        navigationBar.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> {
                    // 페이지 1의 프래그먼트를 열기
                    val fragment = HomeFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.mainFragment, fragment)
                        .commit()
                }
                R.id.navigation_pet -> {
                    // 페이지 2의 프래그먼트를 열기
                    val fragment = PetFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.mainFragment, fragment)
                        .commit()
                }
                R.id.navigation_information -> {
                    // 페이지 3의 프래그먼트를 열기
                    val fragment = InfoFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.mainFragment, fragment)
                        .commit()
                }
                R.id.navigation_mypage -> {
                    // 페이지 4의 프래그먼트를 열기
                    val fragment = MypageFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.mainFragment, fragment)
                        .commit()
                }
                else -> return@setOnItemSelectedListener false
            }
            true
        }
    }
}

