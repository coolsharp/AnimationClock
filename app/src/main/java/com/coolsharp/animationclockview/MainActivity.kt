package com.coolsharp.animationclockview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.coolsharp.animationclock.ClockView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var clockView = findViewById<ClockView>(R.id.clockView)

        clockView.setTime(60)
    }
}
