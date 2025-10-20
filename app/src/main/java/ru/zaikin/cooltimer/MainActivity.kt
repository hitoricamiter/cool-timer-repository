package ru.zaikin.cooltimer

import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        var downTimer: CountDownTimer = object : CountDownTimer(10000, 1000) {
            override fun onFinish() {
                Log.d("Finish", "done")
            }

            override fun onTick(millisUntilFinished: Long) {
                Log.d("Tick", "left $millisUntilFinished")
            }

        }

        downTimer.start()

        /*val handler: Handler = Handler()

        val runnable = object : Runnable {
            override fun run() {
                handler.postDelayed(this, 2000)
                Log.d("Runnable", "2 seconds passed")
            }
        }

        handler.post(runnable)*/


    }
}
