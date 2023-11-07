package com.gvn.osdigital.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.gvn.osdigital.R
import com.gvn.osdigital.databinding.FragmentLogoSplashBinding


class LogoSplashFragment : Fragment() {

    private var _binding: FragmentLogoSplashBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLogoSplashBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root


    }

    //tela é exibida
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //exibe os 3000mls e chama o metodo checAu, para outra tela
        Handler(Looper.getMainLooper()).postDelayed(this::checkAu, 3000)
    }


    //Verifica se o usuario já esta logado e pula a tela de login
    private fun checkAu() {

        auth = Firebase.auth
        if (auth.currentUser == null) {
            findNavController().navigate(R.id.navigationAuthent)
        } else {
            findNavController().navigate(R.id.action_logoSplashFragment_to_menuFragment2)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}