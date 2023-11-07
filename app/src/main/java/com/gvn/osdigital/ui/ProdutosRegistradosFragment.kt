package com.gvn.osdigital.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.gvn.osdigital.R
import com.gvn.osdigital.TelaProdutoSalvo

import com.gvn.osdigital.databinding.FragmentProdutosRegistradosBinding
import com.gvn.osdigital.helper.FirebaseHelper

import com.gvn.osdigital.model.RegistroProdutos

import com.gvn.osdigital.ui.adapter.RegistroProutosAdapter


class ProdutosRegistradosFragment : Fragment() {

    private var _binding: FragmentProdutosRegistradosBinding? = null
    private val binding get() = _binding!!

    private lateinit var registroProdutosAdapter: RegistroProutosAdapter
    private lateinit var registroProdutos: RegistroProdutos
    private val registrolistproduto = mutableListOf<RegistroProdutos>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProdutosRegistradosBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment


        return binding.root


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        clickButton()
        getProdutos()

    }


    private fun clickButton() {
        binding.floatButton2.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_cadastroProdutosFragment)


        }
    }

    //Pegar os registros da Os Criada
    private fun getProdutos() {
        FirebaseHelper
            .getDatabase()
            .child("regitroProdutos")
            .child(FirebaseHelper.getUserId() ?: "")
            .addValueEventListener(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {

                        //limpar a lista antes para não duplicar
                        registrolistproduto.clear()

                        for (snap in snapshot.children) {
                            val registro =
                                snap.getValue(RegistroProdutos::class.java) as RegistroProdutos
                            registrolistproduto.add(registro)
                        }
                        binding.progres.isVisible = false
                        binding.textCarregando3.text = ""

                        //coloca a ultima os para er a primeira ser exibida
                        registrolistproduto.reverse()

                        //inicia o métodoo
                        initAdapter()

                    } else {
                        binding.textCarregando3.text = "Nenhum produto Registrado\n" +
                                " Aperte o botão + abaixo\n" +
                                " para Adicionar Produtos"
                        binding.progres.isVisible = false
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                    Toast.makeText(requireContext(), "ERRO", Toast.LENGTH_SHORT).show()
                }

            })

    }

    //colocar informações no adappter
    private fun initAdapter() {
        binding.recyPRSaves3.layoutManager = LinearLayoutManager(requireContext())
        binding.recyPRSaves3.setHasFixedSize(true)

        registroProdutosAdapter = RegistroProutosAdapter({ registroProdutos ->


            //evento clique para tela de produtos salvos
            val intent = Intent(context, TelaProdutoSalvo::class.java)
            intent.putExtra("dadosProdutosSave", registroProdutos)
            startActivity(intent)
        }, requireContext(), registrolistproduto)
        binding.recyPRSaves3.adapter = registroProdutosAdapter


    }


}