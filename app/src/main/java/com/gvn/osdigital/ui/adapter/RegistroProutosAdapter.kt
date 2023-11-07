package com.gvn.osdigital.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.gvn.osdigital.databinding.ActivityTelaProdutoSalvoBinding
import com.gvn.osdigital.databinding.ItemAdapterProdutosBinding
import com.gvn.osdigital.helper.FirebaseHelper
import com.gvn.osdigital.model.RegistroProdutos

class RegistroProutosAdapter(
   private val detalheOnClick: (RegistroProdutos)-> Unit,
    private val context: Context,
    private val listProduct : List<RegistroProdutos>
        ): Adapter<RegistroProutosAdapter.RegistroProdutoViewHolder>(){

            inner class RegistroProdutoViewHolder(val binding: ItemAdapterProdutosBinding):
                    RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegistroProdutoViewHolder {
        return RegistroProdutoViewHolder(
            ItemAdapterProdutosBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )


        )

    }

    override fun onBindViewHolder(holder: RegistroProdutoViewHolder, position: Int) {
        val recyclerProdutos = listProduct[position]

        holder.binding.txNProduto.text= recyclerProdutos.produto
        holder.binding.txQuantidade.text= recyclerProdutos.quantidade




        //evento clique
        holder.binding.card.setOnClickListener {

            detalheOnClick(recyclerProdutos) // Passar o produto correto para a função de clique

           // Ignora Mudança teste IA
//            var id = registroProdutos.id
//            detalheOnClick(RegistroProdutos(id = id.toString()))


           // detalhes()
        }
    }

    override fun getItemCount(): Int {
        return listProduct.size
    }

//  //  private fun detalhes(){
//
//
//
//
//            val intent = Intent(context, ActivityTelaProdutoSalvoBinding::class.java)
//
//            Toast.makeText(context, "Exibir detalhes", Toast.LENGTH_SHORT).show()
//
//    }
}
