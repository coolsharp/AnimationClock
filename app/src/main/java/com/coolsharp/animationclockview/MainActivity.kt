package com.coolsharp.animationclockview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import com.coolsharp.animationclock.ClockView

class MainActivity : AppCompatActivity() {

    var milisecond = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var clockView = findViewById<ClockView>(R.id.clockView)

        var milisecond = 60

        object : CountDownTimer(60000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                clockView.setTime(milisecond)
                milisecond--
            }

            override fun onFinish() {
                clockView.setTime(0)
            }
        }.start()
    }
}
