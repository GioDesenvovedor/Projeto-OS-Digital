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
import com.gvn.osdigital.databinding.FragmentCadastroBinding
import com.gvn.osdigital.helper.FirebaseHelper


class CadastroFragment : Fragment() {

    private var _binding: FragmentCadastroBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCadastroBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        btnSalvar()
        btnJatemCad()
    }

    fun btnSalvar(){
        binding.btnSalvar.setOnClickListener {validation()
        }
    }


    fun validation() {
        val nome = binding.editNome.text.toString().trim()
        val email = binding.editEmail.text.toString().trim()
        val senha = binding.editSenha.text.toString().trim()

        if (nome.isNotEmpty()) {
            if (email.isNotEmpty()) {
                if (senha.isNotEmpty()) {

                    binding.progresBar.isVisible = true
                    saveCadastro(nome, email, senha)

                } else {
                    Toast.makeText(requireContext(), "Preencha o campo senha", Toast.LENGTH_SHORT)
                        .show()
                }

            } else {
                Toast.makeText(requireContext(), "Preencha o campo email", Toast.LENGTH_SHORT)
                    .show()
            }

        } else {
            Toast.makeText(requireContext(), "Preencha o campo nome", Toast.LENGTH_SHORT).show()
        }
    }

    fun saveCadastro(nome: String, email: String, senha: String) {
        auth.createUserWithEmailAndPassword(email, senha)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    findNavController().navigate(R.id.menuFragment)

                    // Sign in success, update UI with the signed-in user's information
//                    Log.d(TAG, "createUserWithEmail:success")
//                    val user = auth.currentUser
//                    updateUI(user)
                } else {

                    Toast.makeText(requireContext(), FirebaseHelper.validError(
                        task.exception?.message ?: ""), Toast.LENGTH_SHORT).show()
                    //validação de erros
                   // Log.i("INFOTESTE","loginUser: ${task.exception?.message}")
                    binding.progresBar.isVisible = false
                }
            }

    }

    private fun btnJatemCad(){
        binding.txtJTemcliqueAqui.setOnClickListener {
            findNavController().navigate((R.id.action_cadastroFragment_to_loginFragment))
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}