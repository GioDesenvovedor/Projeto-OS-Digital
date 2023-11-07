package com.gvn.osdigital.auth

import android.os.Bundle
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
import com.gvn.osdigital.databinding.FragmentRecuperarSenhaBinding


class RecuperarSenhaFragment : Fragment() {

    private var _binding: FragmentRecuperarSenhaBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecuperarSenhaBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth

        btnInits()
        btnJatemCad()
    }

    fun btnInits() {
        binding.btnEnviar.setOnClickListener {validation()}
    }


    private fun validation() {

        val email = binding.editEmail.text.toString().trim()

        if (email.isNotEmpty()) {

                binding.progresBar.isVisible = true
                loginUser(email)

        } else {
            Toast.makeText(requireContext(), "Preencha o campo email", Toast.LENGTH_SHORT)
                .show()
        }


        binding.progresBar.isVisible = false
    }

    private fun loginUser(email: String) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        requireContext(),
                        "Email de recuperação de senha foi enviado!",
                        Toast.LENGTH_SHORT,
                    ).show()
                    // Sign in success, update UI with the signed-in user's information
//                    Log.d(TAG, "createUserWithEmail:success")
//                    val user = auth.currentUser
//                    updateUI(user)
                } else {

                    Toast.makeText(
                        requireContext(),
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()

                }
            }

    }

    private fun btnJatemCad(){
        binding.txtJTcliqueAqui.setOnClickListener {
            findNavController().navigate((R.id.action_recuperarSenhaFragment_to_loginFragment))
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}