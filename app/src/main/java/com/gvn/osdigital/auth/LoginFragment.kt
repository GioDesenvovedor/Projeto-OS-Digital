package com.gvn.osdigital.auth

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.gvn.osdigital.R
import com.gvn.osdigital.databinding.FragmentApresentacaoBinding
import com.gvn.osdigital.databinding.FragmentLoginBinding
import com.gvn.osdigital.helper.FirebaseHelper


class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth

        btnInits()
    }

    fun btnInits() {
        binding.btnlogin.setOnClickListener { btnAction() }

        binding.txtRecuperSenha.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_recuperarSenhaFragment3)
        }
    }
//
////        binding.face.setOnClickListener {
////            findNavController().navigate()
////        }
////
////        binding.gmail.setOnClickListener {
////            findNavController().navigate(R.id.)
////        }
//
//        binding.btnLogin.setOnClickListener {
//            findNavController().navigate(R.id.action_loginFragment_to_menuFragment)
//        }
//

//
//    }

    private fun btnAction() {


        val email = binding.editEmail.text.toString().trim()
        val senha = binding.editSenha.text.toString().trim()


        if (email.isNotEmpty()) {
            if (senha.isNotEmpty()) {

                binding.progresBar.isVisible = true
                loginUser(email, senha)

            } else {
                Toast.makeText(
                    requireContext(),
                    "Preencha o campo senha",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }

        } else {
            Toast.makeText(requireContext(), "Preencha o campo email", Toast.LENGTH_SHORT)
                .show()
        }


    }

    private fun loginUser(email: String, senha: String) {
        auth.signInWithEmailAndPassword(email, senha)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    findNavController().navigate(R.id.menuFragment)


                } else {
                    Toast.makeText(
                        requireContext(),
                        FirebaseHelper.validError(task.exception?.message ?: ""),
                        Toast.LENGTH_SHORT
                    ).show()
                    //validação de erros com logcat
                    // Log.i("INFOTESTE","loginUser: ${task.exception?.message}")
                    binding.progresBar.isVisible = false

                }
            }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}