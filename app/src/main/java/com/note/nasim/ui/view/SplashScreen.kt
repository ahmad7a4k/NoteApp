package com.note.nasim.ui.view

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.note.nasim.R
import android.util.Pair
import android.view.View
import android.widget.ImageView

@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val logo:ImageView = findViewById(R.id.logo)


        Handler(Looper.getMainLooper()).postDelayed({
            val options = ActivityOptions.makeSceneTransitionAnimation(
                this,
                Pair<View, String>(logo, "logoTrn"))
            val intent = Intent(this, Dashboard::class.java)
            startActivity(intent,options.toBundle())
            finish()

        }, 1200)

    }
}