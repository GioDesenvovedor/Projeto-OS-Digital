package com.gvn.osdigital

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.gvn.osdigital.databinding.ActivityDetalhesClientesBinding
import com.gvn.osdigital.helper.FirebaseHelper
import com.gvn.osdigital.model.RegistroClientes

class DetalhesClientes : AppCompatActivity() {
    private lateinit var binding: ActivityDetalhesClientesBinding
    private lateinit var registroClientes: RegistroClientes
    private lateinit var  auth: FirebaseAuth
    val egistroclientelist = mutableListOf<RegistroClientes>()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityDetalhesClientesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //exibir dados salvos através da chave key
        val dadosClientSave = intent.getParcelableExtra<RegistroClientes>("dadosClient")
        if (dadosClientSave != null) {
            registroClientes = dadosClientSave // Atribuição dos dados para a variável registroClientes
            displayClientDetails(dadosClientSave)
        }else{
            Toast.makeText(this,"Nenhum dado encontrado",Toast.LENGTH_SHORT).show()
        }

        binding.btnDeleteClient.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Remover Cliente?")
                .setMessage("Deseja remover este Cliente?")
                .setNegativeButton("NÃO") { dialog, posicao -> }
                .setPositiveButton("SIM") { dialog, posicao ->
                    delete(registroClientes)
                }.create()
                .show()
        }
    }

    private fun displayClientDetails(registroClientes: RegistroClientes) {

        binding.EditNomEmpresa.setText(registroClientes.nomeEmpresa)
        binding.editEndereco.setText(registroClientes.endEmpresa)
        binding.EditTelefone.setText(registroClientes.telefone)
        binding.EditNumero.setText(registroClientes.numero)
        binding.EditCnpj.setText(registroClientes.cnpj)
        binding.EditCep.setText(registroClientes.cep)
        binding.EditResponsavel.setText(registroClientes.nomeResponsavel)
        binding.EditEmail.setText(registroClientes.email)
    }

    private fun delete(registroClientes: RegistroClientes){

       // try {

            FirebaseHelper
                .getDatabase()
                .child("registroClientes")
                .child(FirebaseHelper.getUserId() ?: "")
                .child(registroClientes.id)
                .removeValue()


        //verificar o ID que sera excluido
        Log.i("T", "INFOR, ${registroClientes.id}, ${registroClientes.nomeEmpresa}")
              /*  .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.i("T", "INFOR, ${registroClientes.id}, ${registroClientes.nomeEmpresa}")
                        Toast.makeText(this, "Cliente Removido, ${registroClientes.id}", Toast.LENGTH_SHORT).show()
                        finish()

                    } else {
                        Toast.makeText(
                            this,
                            "ERRO: Ao tentar Remover o Cliente, ${task.hashCode()} ",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }

        }catch (e:Exception){
            Log.i("Inf", "ERRO AO DELETAR: ${e.message}")
        }*/


    }
}