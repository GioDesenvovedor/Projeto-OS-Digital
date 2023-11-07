package com.gvn.osdigital.auth

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.gvn.osdigital.R
import com.gvn.osdigital.databinding.FragmentApresentacaoBinding
import com.gvn.osdigital.databinding.FragmentLoginBinding


class ApresentacaoFragment : Fragment() {


    private var _binding: FragmentApresentacaoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentApresentacaoBinding.inflate(inflater,container, false)
        // Inflate the layout for this fragment
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnAction()
    }

    fun btnAction(){

        binding.btnCadastro.setOnClickListener {
            findNavController().navigate(R.id.action_apresentacaoFragment_to_cadastroFragment2)
        }

        binding.btnTemCadastro.setOnClickListener {
            findNavController().navigate(R.id.action_apresentacaoFragment_to_loginFragment2)
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}