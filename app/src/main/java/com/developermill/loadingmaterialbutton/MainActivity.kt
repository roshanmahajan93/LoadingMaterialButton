package com.developermill.loadingmaterialbutton

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.core.content.res.ResourcesCompat
import com.developermill.loadingmaterialbutton.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.presenter = this

        val normal =
            ResourcesCompat.getFont(this, R.font.bebas_neue_regular);
        binding.signin.setFont(normal!!)

        binding.signin.setButtonOnClick{

            binding.signin.onStartLoading()
            Handler().postDelayed({
                binding.signin.onStopLoading()
            }, 500)

        }





    }




}