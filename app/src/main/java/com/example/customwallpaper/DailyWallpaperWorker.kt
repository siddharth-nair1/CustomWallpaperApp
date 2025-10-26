package com.example.customwallpaper

import android.app.WallpaperManager
import android.content.Context
import android.graphics.*
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.ChronoUnit

class DailyWallpaperWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {

    override fun doWork(): Result {
        try {
            val prefs = applicationContext.getSharedPreferences("cw_prefs", Context.MODE_PRIVATE)
            val targetStr = prefs.getString("target_date", null)
            val today = LocalDate.now(ZoneId.systemDefault())

            val daysLeft = if (targetStr == null) {
                0L
            } else {
                try {
                    val target = LocalDate.parse(targetStr)
                    ChronoUnit.DAYS.between(today, target)
                } catch (e: Exception) {
                    0L
                }
            }

            val text = "${if (daysLeft >= 0) daysLeft else 0} left, Keep going!"

            // create a black bitmap the size of typical phone wallpaper
            val width = 1080
            val height = 1920
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            canvas.drawColor(Color.BLACK)

            // draw the text centered
            val paint = Paint().apply {
                color = Color.WHITE
                isAntiAlias = true
                textSize = 80f
                typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
                textAlign = Paint.Align.CENTER
            }

            val x = width / 2f
            val y = height / 2f - (paint.descent() + paint.ascent()) / 2
            canvas.drawText(text, x, y, paint)

            val wm = WallpaperManager.getInstance(applicationContext)
            wm.setBitmap(bitmap)
            return Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.failure()
        }
    }
}
