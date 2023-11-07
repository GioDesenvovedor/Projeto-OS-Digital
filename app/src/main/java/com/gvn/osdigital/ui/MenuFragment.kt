package com.gvn.osdigital.ui

import android.app.AlertDialog
import android.graphics.fonts.Font
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.gvn.osdigital.R
import com.gvn.osdigital.databinding.FragmentMenuBinding
import com.gvn.osdigital.databinding.ItemAdapterClienteBinding
import com.gvn.osdigital.ui.adapter.ViewPagerAdapter


class MenuFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        configTabLayout()
        auth = Firebase.auth
        clickButton()

    }

    private fun clickButton() {
        binding.imageButton.setOnClickListener {
            logoutUser()
        }
    }

    private fun logoutUser() {
        AlertDialog.Builder(context)
            .setTitle("Logoff")
            .setMessage("Deseja realmente sair?")
            .setNegativeButton("NÃO") { dialog, posicao -> }
            .setPositiveButton("SIM") { dialog, posicao ->
        auth.signOut()
        findNavController().navigate(R.id.action_menuFragment_to_navigationAuthent)
            }
            .create()
            .show()

    }

//      implementar menu de canto
//    private fun menuCanto(){
//        addMenuProvider(
//            object : MenuProvider{
//                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
//                   menuInfater.inflate(R.menu.menu_de_canto, menu)
//                }
//
//                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
//                    when(menuItem.itemId){
//                    R,id.item_perfil ->}
//                      startActivity(Itent(applicationContext,CadastroActivity::class.java))
//                }
//                       R,id.item_config ->}
////                      startActivity(Itent(applicationContext,ConigActivity::class.java))
//            }
//        )
//    }

    private fun configTabLayout() {
        val adapter = ViewPagerAdapter(requireActivity())
        binding.viewpager.adapter = adapter


        adapter.addFragment(OsRegistradaFragment(), "O.S Registradas")
        adapter.addFragment(ClientesRegistradosFragment(), "Clientes Registrados")
        adapter.addFragment(ProdutosRegistradosFragment(), "Produtos")

        binding.viewpager.offscreenPageLimit = adapter.itemCount

        TabLayoutMediator(
            binding.tabs, binding.viewpager
        ) { tab, position ->
            tab.text = adapter.getTitle(position)

        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}