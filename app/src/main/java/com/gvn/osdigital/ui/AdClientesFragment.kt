package com.gvn.osdigital.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController

import com.gvn.osdigital.databinding.FragmentAdClientesBinding
import com.gvn.osdigital.helper.FirebaseHelper
import com.gvn.osdigital.model.RegistroClientes


class AdClientesFragment : Fragment() {

    private var _binding: FragmentAdClientesBinding? = null
    private val binding get() = _binding!!
    private lateinit var registroClientes: RegistroClientes

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAdClientesBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        clickBtn()

    }



    private fun clickBtn() {
        binding.btnSalvarClient.setOnClickListener {
            validation()

        }

    }

    private fun validation() {
        val nome = binding.EditNomEmpresa.text.toString().trim()
        val responsavel = binding.EditResponsavel.text.toString().trim()
        val endEmpresa = binding.editEndereco.text.toString().trim()
        val telefone = binding.EditTelefone.text.toString().trim()
        val numero = binding.EditNumero.text.toString().trim()
        val cnpj = binding.EditCnpj.text.toString().trim()
        val cep = binding.EditCep.text.toString().trim()
        val email = binding.EditEmail.text.toString().trim()



        if (nome.isNotEmpty() && responsavel.isNotEmpty()) {



            registroClientes = RegistroClientes()

            registroClientes.nomeEmpresa = nome
            registroClientes.nomeResponsavel = responsavel
            registroClientes.endEmpresa = endEmpresa
            registroClientes.telefone = telefone
            registroClientes.numero = numero
            registroClientes.cnpj = cnpj
            registroClientes.cep = cep
            registroClientes.email = email


            saveOs()

        } else {
            Toast.makeText(
                requireContext(),
                "Os campos: NOME DA EMPRESA e Responsavel, não podem ficar em branco!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun saveOs() {

        FirebaseHelper.getDatabase()
            .child("registroClientes")
            .child(FirebaseHelper.getUserId() ?: "")
            .child(registroClientes.id)
            .setValue(registroClientes)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    findNavController().popBackStack()
                    Toast.makeText(requireContext(), "Salvo com sucesso !", Toast.LENGTH_SHORT)
                        .show()
                } else {

                    Toast.makeText(requireContext(), "Erro ao salvar Registro !", Toast.LENGTH_SHORT)
                        .show()
                }
            }.addOnFailureListener {
               // binding.progresBar.isVisible = false
                Toast.makeText(requireContext(), "Erro ao salvar registro 2!", Toast.LENGTH_SHORT).show()
            }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}