package com.zorbeytorunoglu.tabuuplus.presentation.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zorbeytorunoglu.tabuuplus.R
import com.zorbeytorunoglu.tabuuplus.databinding.FragmentSplashBinding

class SplashFragment: Fragment() {

    private lateinit var binding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSplashBinding.inflate(inflater, container, false)


        return binding.root
    }

}