package com.gvn.osdigital.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.gvn.osdigital.DetalhesClientes
import com.gvn.osdigital.R
import com.gvn.osdigital.databinding.FragmentClientesRegistradosBinding
import com.gvn.osdigital.helper.FirebaseHelper
import com.gvn.osdigital.model.RegistroClientes

import com.gvn.osdigital.ui.adapter.RegistroClientesAdapter


class ClientesRegistradosFragment : Fragment() {

    private var _binding : FragmentClientesRegistradosBinding? = null
    private val binding get() = _binding!!

    private lateinit var registroClientesAdapter: RegistroClientesAdapter

    private val registroListClient = mutableListOf<RegistroClientes>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentClientesRegistradosBinding.inflate(inflater, container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickButton()
        getClient()

    }

    private fun clickButton(){
        binding.floatButton3.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_adClientesFragment)

        }
    }



    //Pegar os registros da Os Criada
    private fun getClient(){
        FirebaseHelper
            .getDatabase()
            .child("registroClientes")
            .child(FirebaseHelper.getUserId()?:"")
            .addValueEventListener(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){

                        //limpar a lista antes para não duplicar
                        registroListClient.clear()

                        for (snap in snapshot.children){
                            val registro = snap.getValue(RegistroClientes::class.java) as RegistroClientes
                            registroListClient.add(registro)
                        }
                        binding.progres.isVisible = false
                        binding.textCarregando.text = ""

                        //coloca a ultima os para er a primeira ser exibida
                        registroListClient.reverse()

                        //inicia o métodoo
                        initAdapter()

                    }else{
                        binding.textCarregando.text = "Nenhuma Cliente Registrado"
                        binding.progres.isVisible = false
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                    Toast.makeText(requireContext(),"ERRO", Toast.LENGTH_SHORT).show()
                }

            } )

    }
    //colocar informações no adappter
    private fun initAdapter(){
        binding.recyClientSaves.layoutManager = LinearLayoutManager(requireContext())
        binding.recyClientSaves.setHasFixedSize(true)

        registroClientesAdapter = RegistroClientesAdapter({registroClientes ->

            //evento cliqque para tela detalhes
            val intent = Intent(context,DetalhesClientes::class.java)
            intent.putExtra("dadosClient", registroClientes)
            startActivity(intent)
        },requireContext(),registroListClient)

        binding.recyClientSaves.adapter = registroClientesAdapter


    }

    private fun delete(){

        FirebaseHelper
            .getDatabase()
            .child("registroClientes")
            .child(FirebaseHelper.getUserId() ?: "")
            .removeValue()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
