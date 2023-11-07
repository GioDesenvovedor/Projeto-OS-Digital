package com.gvn.osdigital

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.gvn.osdigital.databinding.FragmentCadastroProdutosBinding
import com.gvn.osdigital.helper.FirebaseHelper
import com.gvn.osdigital.model.RegistroProdutos

class CadastroProdutosFragment : Fragment() {

    private var _binding: FragmentCadastroProdutosBinding? = null
    private val binding get() = _binding!!
    private lateinit var registroProdutos: RegistroProdutos

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCadastroProdutosBinding.inflate(inflater, container, false)
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
        val produto = binding.EditProduto.text.toString().trim()
        val quantidade = binding.EditQuantidade.text.toString().trim()
        val valor = binding.editValorProduct.text.toString().trim()
        val modelo = binding.editModelo.text.toString().trim()
        val numeroserie = binding.EditSN.text.toString().trim()
        val prcusto = binding.EditPrecoCusto.text.toString().trim()

        if (produto.isNotEmpty() && quantidade.isNotEmpty() ){


            registroProdutos = RegistroProdutos()
            registroProdutos.produto = produto
            registroProdutos.quantidade = quantidade
            registroProdutos.valor = valor
            registroProdutos.modelo = modelo
            registroProdutos.numeroserie = numeroserie
            registroProdutos.prcusto = prcusto

            saveProduto()
        }else {
            Toast.makeText(
                requireContext(),
                "Preencha os campos Produto e Quantidade",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun saveProduto(){

        FirebaseHelper.getDatabase()
            .child("regitroProdutos")
            .child(FirebaseHelper.getUserId()?:"")
            .child(registroProdutos.id)
            .setValue(registroProdutos)
            .addOnCompleteListener {task ->
                if (task.isSuccessful){
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