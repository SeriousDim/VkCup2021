package com.example.vk_cup_2021

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.vk_cup_2021.tasks.second.SecondActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }

    fun runSecondActivity(v: View){
        var intent = Intent(this, SecondActivity::class.java)
        startActivity(intent)
    }
}