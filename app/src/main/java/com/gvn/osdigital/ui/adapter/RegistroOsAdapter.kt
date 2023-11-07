package com.gvn.osdigital.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.gvn.osdigital.databinding.ItemAdapterBinding
import com.gvn.osdigital.model.RegistroOs

class RegistroOsAdapter(

    private val detalheOnclik:(RegistroOs)->Unit,
    private val context: Context,
    private val registerList: List<RegistroOs>
) : Adapter<RegistroOsAdapter.RegistoOsViewHolder>() {

    //aramazena a informações do recyclreView com o item, neste caso item_adapter
    inner class RegistoOsViewHolder(val binding: ItemAdapterBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegistoOsViewHolder {
        return RegistoOsViewHolder(
            ItemAdapterBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }


    //atribui os valores
    override fun onBindViewHolder(holder: RegistoOsViewHolder, position: Int) {
        val recyRegistroOs = registerList[position]

        //aqui ele vai setar os textos que estáo do firebase no cardView da recycler
        holder.binding.nomeClientRegistOs.text = recyRegistroOs.nome
        holder.binding.textNOS.text = recyRegistroOs.numeroOs
        holder.binding.textDefeito.text = recyRegistroOs.defeito
        holder.binding.textDataOs.text = recyRegistroOs.data
        holder.binding.textValor.text = recyRegistroOs.valor

        //evento clique
        holder.binding.card1.setOnClickListener {
            detalheOnclik(recyRegistroOs) // Passar o produto correto para a função de clique

        }
    }

    // Recupera a quantidde de itens
    override fun getItemCount(): Int {
        return registerList.size
    }
}