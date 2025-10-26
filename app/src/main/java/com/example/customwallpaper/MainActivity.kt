package com.example.customwallpaper

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import java.time.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var tvTarget: TextView
    private lateinit var btnPick: Button
    private lateinit var btnSchedule: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvTarget = findViewById(R.id.tvTarget)
        btnPick = findViewById(R.id.btnPickDate)
        btnSchedule = findViewById(R.id.btnSchedule)

        updateTargetText()

        btnPick.setOnClickListener {
            val now = LocalDate.now()
            val dp = DatePickerDialog(this, { _, year, month, dayOfMonth ->
                val selected = LocalDate.of(year, month + 1, dayOfMonth) // month is 0-based from picker
                val prefs = getSharedPreferences("cw_prefs", MODE_PRIVATE)
                prefs.edit().putString("target_date", selected.toString()).apply()
                updateTargetText()
            }, now.year, now.monthValue - 1, now.dayOfMonth)
            dp.show()
        }

        btnSchedule.setOnClickListener {
            scheduleDailyAt8()
        }

        // schedule on first run as well
        scheduleDailyAt8()
    }

    private fun updateTargetText() {
        val prefs = getSharedPreferences("cw_prefs", MODE_PRIVATE)
        val target = prefs.getString("target_date", null)
        tvTarget.text = if (target == null) {
            "Target date: not set"
        } else {
            "Target date: $target"
        }
    }

    private fun scheduleDailyAt8() {
        val now = ZonedDateTime.now(ZoneId.systemDefault())
        val next8 = now.withHour(8).withMinute(0).withSecond(0).withNano(0)
            .let { if (it.isBefore(now) || it.isEqual(now)) it.plusDays(1) else it }

        val initialDelay = Duration.between(now, next8).toMillis()
        val dailyRequest = PeriodicWorkRequestBuilder<DailyWallpaperWorker>(24, TimeUnit.HOURS)
            .setInitialDelay(initialDelay, TimeUnit.MILLISECONDS)
            .setConstraints(Constraints.Builder().setRequiresBatteryNotLow(false).build())
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "daily_wallpaper_work",
            ExistingPeriodicWorkPolicy.REPLACE,
            dailyRequest
        )
    }
}
