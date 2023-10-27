package com.zorbeytorunoglu.tabuuplus.presentation.ui.fragment

import android.animation.Animator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.zorbeytorunoglu.tabuuplus.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment: Fragment() {

    private lateinit var binding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSplashBinding.inflate(inflater, container, false)

        binding.lottieAnimationView.addAnimatorListener(object : Animator.AnimatorListener {

            override fun onAnimationStart(p0: Animator) {}

            override fun onAnimationEnd(p0: Animator) {
                Navigation.findNavController(binding.root).navigate(
                    SplashFragmentDirections.actionSplashFragmentToMainFragment()
                )
            }

            override fun onAnimationCancel(p0: Animator) {}
            override fun onAnimationRepeat(p0: Animator) {}

        })

        return binding.root
    }

}