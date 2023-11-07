package com.gvn.osdigital.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.gvn.osdigital.R
import com.gvn.osdigital.databinding.ItemAdapterClienteBinding
import com.gvn.osdigital.model.RegistroClientes
import com.gvn.osdigital.ui.ClientesRegistradosFragment

class RegistroClientesAdapter(
    private val detalheOnclick: (RegistroClientes)->Unit,
    private val context: Context,
    private val registerListC: List<RegistroClientes>
) : Adapter<RegistroClientesAdapter.RegistroClientViewHolder>() {


    inner class RegistroClientViewHolder(val binding: ItemAdapterClienteBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegistroClientViewHolder {

//        val inflater  = LayoutInflater.from(parent.context)
//        val itenView = ItemAdapterClienteBinding.inflate(
//            inflater, parent, false
//        )
//        return RegistroClientViewHolder(itenView)

        return RegistroClientViewHolder(
            ItemAdapterClienteBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )


    }

    override fun onBindViewHolder(holder: RegistroClientViewHolder, position: Int) {
        val recyclerRegClient = registerListC[position]

        holder.binding.nomeEmpresa.text = recyclerRegClient.nomeEmpresa
        holder.binding.textContato.text = recyclerRegClient.nomeResponsavel
//        holder.binding.nomeEmpresa.text = recyclerRegClient.nomeEmpresa
//        holder.binding.nomeEmpresa.text = recyclerRegClient.nomeEmpresa
//        holder.binding.nomeEmpresa.text = recyclerRegClient.nomeEmpresa

        //evento click
        holder.binding.card.setOnClickListener {

            detalheOnclick(recyclerRegClient)// Passar o produto correto para a função de clique

            Toast.makeText(context, "Exibir detalhes", Toast.LENGTH_SHORT).show()

        }



        // longClick()


    }

    override fun getItemCount(): Int {
        return registerListC.size
    }

}
//    private fun longClick(){
//        //delete cliente
//
//
//
//
//    }

