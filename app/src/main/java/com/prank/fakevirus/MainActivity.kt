package com.malewarevirus

import android.media.MediaPlayer
import android.media.RingtoneManager
import android.os.*
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val statusText = findViewById<TextView>(R.id.statusText)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator

        progressBar.max = 100
        progressBar.progress = 0

        // 🔔 Alarm bawaan sistem (Samsung = bunyi khas Samsung)
        val alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        mediaPlayer = MediaPlayer.create(this, alarmUri)
        mediaPlayer?.isLooping = true
        mediaPlayer?.start()

        val handler = Handler(Looper.getMainLooper())
        handler.post(object : Runnable {
            override fun run() {
                if (progressBar.progress < 100) {
                    progressBar.progress += 4

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        vibrator.vibrate(
                            VibrationEffect.createOneShot(
                                60,
                                VibrationEffect.DEFAULT_AMPLITUDE
                            )
                        )
                    }

                    handler.postDelayed(this, 250)
                } else {
                    mediaPlayer?.stop()
                    mediaPlayer?.release()
                    mediaPlayer = null

                    statusText.text = "hp lu kena virus di goblokin mau hub no:6283194854924"
                    statusText.setTextColor(0xFF00FF00.toInt())
                }
            }
        })
    }

    override fun onDestroy() {
        mediaPlayer?.release()
        super.onDestroy()
    }
}
