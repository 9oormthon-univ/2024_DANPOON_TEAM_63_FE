package com.example.youthspacefinder.presentation

import android.animation.Animator
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.youthspacefinder.R
import com.example.youthspacefinder.databinding.ActivityMainBinding
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class MainActivity : AppCompatActivity() {

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        binding.lottieAnimation.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
                // 애니메이션 시작
            }

            override fun onAnimationEnd(animation: Animator) {
                // 애니메이션 종료
                binding.lottieAnimation.visibility = View.GONE
            }

            override fun onAnimationCancel(animation: Animator) {
                // 애니메이션 취소
            }

            override fun onAnimationRepeat(animation: Animator) {
                // 애니메이션이 시작된 이후 반복될 때
            }

        })

        getKeyHash().let {
            Log.d("test", "$it")
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun getKeyHash() {
        val packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNING_CERTIFICATES)
        for (signature in packageInfo.signingInfo.apkContentsSigners) {
            try {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.d("getKeyHash", "key hash: ${Base64.encodeToString(md.digest(), Base64.NO_WRAP)}")
            } catch (e: NoSuchAlgorithmException) {
                Log.w("getKeyHash", "Unable to get MessageDigest. signature=$signature", e)
            }
        }
    }
}