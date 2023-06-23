package com.bahadir.animelist.presentation.activity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewAnimationUtils
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.airbnb.lottie.LottieAnimationView
import com.bahadir.animelist.R
import com.bahadir.animelist.common.extensions.collectIn
import com.bahadir.animelist.common.extensions.gone
import com.bahadir.animelist.common.extensions.navigationHide
import com.bahadir.animelist.common.extensions.setDecoder
import com.bahadir.animelist.common.extensions.visible
import com.bahadir.animelist.databinding.ActivityMainBinding
import com.bahadir.animelist.delegation.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Integer.max
import java.time.Clock
import java.time.Instant
import java.time.temporal.ChronoUnit


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding by viewBinding(ActivityMainBinding::inflate)
    private val viewModel: MainVM by viewModels()
    private val navHostFragment by
    lazy { supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            splashScreen(this)
            setKeepOnScreenCondition {
                viewModel.isCurrentUserExistUseCase().collectIn(this@MainActivity) {
                    viewModel.isLogged = false
                    if (it) {
                        navHostFragment.findNavController()
                            .navigate(R.id.action_nav_graph)
                    }
                }
                viewModel.isLogged
            }

        }
        setContentView(binding.root)
    }

    private fun navController() {
        binding.bottomNavigationView.setupWithNavController(navHostFragment.navController)
        navHostFragment.navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.searchFragment -> {
                    binding.bottomNavigationView.visible()
                    setDecoder(true)
                }

                R.id.homeFragment -> {
                    binding.bottomNavigationView.visible()
                    setDecoder(false)
                    window.navigationHide()
                }

                R.id.scheduleFragment -> {
                    setDecoder(true)
                    binding.bottomNavigationView.visible()
                }

                R.id.animeDetailFragment -> {
                    binding.bottomNavigationView.gone()
                    setDecoder(false)
                }

                else -> {
                    setDecoder(true)
                    binding.bottomNavigationView.gone()
                }
            }
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        window.navigationHide()
    }

    private fun splashScreen(splashScreen: SplashScreen) {
        // gecikmeyi hesaplamak istersek api 26 ve üstü istediği için açıyoruz ama gecikmeyi
        // 0 da versem 10-20 de versam bir fark olmuyor
        splashScreen.setOnExitAnimationListener { vp ->
            val lottieView = findViewById<LottieAnimationView>(R.id.lottieView)
            lottieView.enableMergePathsForKitKatAndAbove(true)

            // We compute the delay to wait for the end of the splash screen icon
            // animation.
            val delay = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val splashScreenAnimationEndTime =
                    Instant.ofEpochMilli(vp.iconAnimationStartMillis + vp.iconAnimationDurationMillis)
                Instant.now(Clock.systemUTC()).until(
                    splashScreenAnimationEndTime, ChronoUnit.MILLIS
                )
            } else {
                DELAY
            }

            //Gecikmeden sonra başlatılır
            lottieView.postDelayed({
                vp.view.alpha = 0f
                vp.iconView.alpha = 0f
                lottieView.playAnimation()
            }, delay)

            lottieView.addAnimatorListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    val contentView = findViewById<View>(android.R.id.content)
                    val animator = ViewAnimationUtils.createCircularReveal(
                        binding.fragmentContainerView,
                        contentView.width / 2,
                        contentView.height / 2,
                        0f,
                        max(contentView.width, contentView.height).toFloat()

                    ).setDuration(DURATION)
                    lottieView.gone()
                    animator.start()
                    binding.fragmentContainerView.visible()
                    navController()
                }
            })
        }
    }

    override fun onResume() {
        super.onResume()
        window.navigationHide()
    }

    companion object {
        private const val DURATION = 600L
        private const val DELAY = 2000L
    }
}