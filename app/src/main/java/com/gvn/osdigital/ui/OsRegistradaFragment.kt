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
import com.gvn.osdigital.DetalhesOsActivity
import com.gvn.osdigital.R
import com.gvn.osdigital.databinding.FragmentOsRegistradaBinding
import com.gvn.osdigital.helper.FirebaseHelper
import com.gvn.osdigital.model.RegistroOs
import com.gvn.osdigital.ui.adapter.RegistroOsAdapter


class OsRegistradaFragment() : Fragment() {

    private var _binding: FragmentOsRegistradaBinding? = null
    private val binding get() = _binding!!

    private lateinit var registroOsAdapter: RegistroOsAdapter
    private lateinit var registrosOs: RegistroOs
    private val registroList = mutableListOf<RegistroOs>()


//    private val firestore by lazy {
//        FirestoreHelper()
//    }





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentOsRegistradaBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        clickButton()
        getOs()
    }

    private fun clickButton() {
        binding.floatButton2.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_criaOsFragment)


        }
    }

    //Pegar os registros da Os Criada
    private fun getOs() {
        FirebaseHelper
            .getDatabase()
            .child("registroOs")
            .child(FirebaseHelper.getUserId() ?: "")
            .addValueEventListener(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {

                        //limpar a lista antes para não duplicar
                        registroList.clear()

                        for (snap in snapshot.children) {
                            val registro = snap.getValue(RegistroOs::class.java) as RegistroOs
                            registroList.add(registro)
                        }
                        binding.progres.isVisible = false
                        binding.textCarregando.text = ""

                        //coloca a ultima os para er a primeira ser exibida
                        registroList.reverse()

                        //inicia o métodoo
                        initAdapter()

                    } else {
                        binding.textCarregando.text = "Nenhuma O.S Registrada"
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
        binding.recyOsSaves.layoutManager = LinearLayoutManager(requireContext())
        binding.recyOsSaves.setHasFixedSize(true)

        registroOsAdapter = RegistroOsAdapter({registrosOs->
            val intent = Intent(context,DetalhesOsActivity::class.java)
            intent.putExtra("dadosOs", registrosOs)
            startActivity(intent)
        }, requireContext(),registroList)
        binding.recyOsSaves.adapter = registroOsAdapter

    }

    //Pegar os registros da Os Criada com FIRESTORE
//    private fun getOs() {
//
//
//        firestore.getDatabase()
//            .collection("osRegistrada")
//            .document(firestore.getUserId() ?: "")
//            .addSnapshotListener { querySnapshot, error ->
//
//                if (querySnapshot!!.exists()) {
//
//                    //limpar a lista antes para não duplicar
//                    registroList.clear()
//
//                 //   for (snap in querySnapshot.id) {
//
//                        val registro = querySnapshot.toObject(RegistroOs::class.java)
//                        registroList.add(registro!!)
//                  //  }
//
//                    binding.progres.isVisible = false
//                    binding.textCarregando.text = ""
//
//                    //coloca a ultima os para er a primeira ser exibida
//                    registroList.reverse()
//
//                    //inicia o métodoo
//                    initAdapter()
//                } else {
//                    binding.textCarregando.text = "Nenhuma O.S Registrada"
//                    binding.progres.isVisible = false
//                }
//
//
//            }
//
////                if (error != null) {
////                    Toast.makeText(requireContext(), "ERRO", Toast.LENGTH_SHORT).show()
////                }
//
//
//    }




//        registroOsAdapter = RegistroOsAdapter(requireContext(), registroList)
//
//        binding.recyOsSaves.adapter = registroOsAdapter
//
//        //delete os
//        binding.recyOsSaves.setOnLongClickListener(object : View.OnLongClickListener {
//            override fun onLongClick(view: View): Boolean {
//                Toast.makeText(
//                    requireContext(),
//                    "Deseja excluir este registro ?",
//                    Toast.LENGTH_SHORT
//                )
//                    .show()
//                return true
//            }
//
//        })


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }
}