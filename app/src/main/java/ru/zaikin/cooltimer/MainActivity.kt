package ru.zaikin.cooltimer

import android.annotation.SuppressLint
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {


    private lateinit var seekBar: SeekBar
    private lateinit var text: TextView
    private lateinit var button: Button

    private var number: Long = 0
    private var isTimerOn: Boolean = false
    private lateinit var downTimer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        seekBar = findViewById<SeekBar>(R.id.seekBar)
        seekBar.max = 600
        seekBar.progress = 30

        text = findViewById<TextView>(R.id.textView2)
        button = findViewById<Button>(R.id.button)

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

                val minutes: Long = (progress / 60).toLong()
                val seconds: Long = progress - (minutes * 60)
                updateTimer(minutes, seconds)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // Можно использовать для действий, когда пользователь начал перемещать ползунок
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Можно использовать для действий, когда пользователь отпустил ползунок
            }
        })


    }

    fun startTimer(view: View) {


        if (!isTimerOn) {
            button.text = "Stop"
            seekBar.isEnabled = false
            isTimerOn = true

            downTimer =
                object : CountDownTimer((seekBar.progress * 1000).toLong(), 1000) {
                    override fun onFinish() {
                        val player: MediaPlayer =
                            MediaPlayer.create(applicationContext, R.raw.bell_sound)
                        player.start()
                        resetTimer()
                    }

                    override fun onTick(millisUntilFinished: Long) {
                        val minutes: Long = millisUntilFinished / 60 / 1000
                        val seconds: Long = millisUntilFinished / 1000 - (minutes * 60)
                        updateTimer(minutes, seconds)
                    }

                }


            downTimer.start()
        } else {
            resetTimer()
        }


    }

    @SuppressLint("SetTextI18n")
    private fun updateTimer(minutes: Long, seconds: Long) {
        var minutesString: String = ""
        var secondsString: String = ""

        minutesString = if (minutes < 10) "0${minutes}" else "$minutes"
        secondsString = if (seconds < 10) "0${seconds}" else "$seconds"

        text.text = "$minutesString:$secondsString"
    }

    private fun resetTimer() {
        downTimer.cancel()
        text.text = "00:30"
        button.text = "Start"
        seekBar.isEnabled = true
        seekBar.progress = 60
        isTimerOn = false
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater: MenuInflater = menuInflater
        menuInflater.inflate(R.menu.timer_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.itemId

        if (id == R.id.action_settings) {
            val openSettings: Intent = Intent(this, SettingsActivity::class.java)
            startActivity(openSettings)
            return true
        } else if (id == R.id.action_about){
            val openAbout: Intent = Intent(this, AboutActivity::class.java)
            startActivity(openAbout)
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}
