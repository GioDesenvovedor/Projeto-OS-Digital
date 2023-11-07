package com.gvn.osdigital

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.isVisible
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.gvn.osdigital.databinding.ActivityTelaProdutoSalvoBinding
import com.gvn.osdigital.helper.FirebaseHelper
import com.gvn.osdigital.model.RegistroProdutos
import com.itextpdf.text.Document
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import java.io.File
import java.io.FileOutputStream

class TelaProdutoSalvo : AppCompatActivity() {
    private lateinit var binding: ActivityTelaProdutoSalvoBinding


    val registrolistproduto = mutableListOf<RegistroProdutos>()

    private lateinit var registroProdutos: RegistroProdutos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTelaProdutoSalvoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGerarPdf.setOnClickListener {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED
                ){
                    gerarPdf()
                }
            }
        }


        //Sugestão IA pegar dados pela chave
        val dadosProdutosSave = intent.getParcelableExtra<RegistroProdutos>("dadosProdutosSave")



        if (dadosProdutosSave != null) {
            displayProductDetails(dadosProdutosSave)
        } else {
            Toast.makeText(this, "Nenhum dado encontrado", Toast.LENGTH_SHORT).show()
        }
    }


    ///gerar pdf
    private fun gerarPdf() {

        val pdfFileName = "meu_arquivo.pdf"
        val directory = File(Environment.getExternalStorageDirectory(), "MeuAppPDF")

        if (!directory.exists()) {
            directory.mkdirs()
        }

        val file = File(directory, pdfFileName)

        try {
            // Crie um documento PDF
            val document = Document()

            // Defina o local de armazenamento do PDF e o nome do arquivo
            val filePath = Environment.getExternalStorageDirectory().toString() + "/ProdutoSalvo.pdf"
            val file = File(filePath)

            val fileOutputStream = FileOutputStream(file)
            PdfWriter.getInstance(document, fileOutputStream)

            // Abra o documento para edição
            document.open()

            // Adicione conteúdo ao PDF (campos de detalhes do produto)
            document.add(Paragraph("Detalhes do Produto"))
            document.add(Paragraph("Produto: ${binding.EditProduto.text}"))
            document.add(Paragraph("Quantidade: ${binding.EditQuantidade.text}"))
            document.add(Paragraph("Número de Série: ${binding.EditSN.text}"))
            document.add(Paragraph("Preço de Custo: ${binding.EditPrecoCusto.text}"))
            document.add(Paragraph("Modelo: ${binding.editModelo.text}"))
            document.add(Paragraph("Valor: ${binding.editValorProduct.text}"))

            // Feche o documento
            document.close()

            // Mostre uma mensagem informando o sucesso
            Toast.makeText(this, "PDF gerado com sucesso!", Toast.LENGTH_SHORT).show()

        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Erro ao gerar PDF", Toast.LENGTH_SHORT).show()
        }
    }

    private fun displayProductDetails(registroProdutos: RegistroProdutos) {
        binding.EditProduto.setText(registroProdutos.produto)
        binding.EditQuantidade.setText(registroProdutos.quantidade)
        binding.EditSN.setText(registroProdutos.numeroserie)
        binding.EditPrecoCusto.setText(registroProdutos.prcusto)
        binding.editModelo.setText(registroProdutos.modelo)
        binding.editValorProduct.setText(registroProdutos.valor)
    }




//        getProdutos()
//        //pegarSalvos()
//        //recuperarProdutos()
//        dadosSalvos(registrolistproduto)
//
//
//    }

//    private fun pegarSalvos() {
//        val dadosProdutosSave = intent.extras
//        if (dadosProdutosSave != null) {
//                intent.getSerializableExtra("dadosProdutosSave") as RegistroProdutos
//
//           //  val dadosProdut =  (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//               // dadosProdutosSave.getParcelable("dadosProdutosSave", RegistroProdutos::class.java)
//        }
//            }
//    }

//    private fun recuperarProdutos() {
//
//
//
//        val extras = intent.extras
//        if (extras != null) {
//
//            dadosProdutos = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//                extras.getParcelable("dadosProdutosSave", RegistroProdutos::class.java)
//
//
//                //codigo era assim mais devido as novas versões ficou como acima
//                //dadosProdutos = extras.getParcelable("dadosProdutosSave", RegistroProdutos::class.java)
//
//            } else {
//                extras.getParcelable("dadosProdutosSave")
//            }
//
//
//        }
//
//
//    }


//    private fun getProdutos() {
//
//        // val registroListProduto = mutableListOf<RegistroProdutos>()
//
//        FirebaseHelper
//            .getDatabase()
//            .child("regitroProdutos")
//            .child(FirebaseHelper.getUserId() ?: "")
//            //.child(registroProdutos.id)
//            .addValueEventListener(object : ValueEventListener {
//
//                override fun onDataChange(snapshot: DataSnapshot) {
//
//
//
//                    //   Log.i("Fireb", snapshot.getValue().toString())
//                    //se tem registro para serem recuperados
//                    if (snapshot.exists()) {
//
//
//                        for (snap in snapshot.children) {
//                            val registro =
//
//                                snap.getValue(RegistroProdutos::class.java) as RegistroProdutos
//                            registrolistproduto.add(registro)
//                        }
//
//                        dadosSalvos(registrolistproduto)
//
//                    } else {
//                        Toast.makeText(applicationContext, "ERRO", Toast.LENGTH_SHORT).show()
//                    }
//
//
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//
//                    Toast.makeText(applicationContext, "ERRO", Toast.LENGTH_SHORT).show()
//                }
//
//            })
//
//    }

//    private fun dadosSalvos(registroListProduto: List<RegistroProdutos>) {
//        if (registroListProduto.isNotEmpty()) {
//
//            val registroProdutos =
//                registroListProduto[0] // Suponha que você deseja usar o primeiro registro da lista
//
//
//
//
//            binding.EditProduto.setText(registroProdutos.produto)
//            binding.EditQuantidade.setText(registroProdutos.quantidade)
//            binding.EditSN.setText(registroProdutos.numeroserie)
//            binding.EditPrecoCusto.setText(registroProdutos.prcusto)
//            binding.editModelo.setText(registroProdutos.modelo)
//            binding.editValorProduct.setText(registroProdutos.valor)
//
//            //binding.editValorProduct.text = registroProdutos.valor
//        } else {
//            Toast.makeText(this, "Nenhum dado encontrado", Toast.LENGTH_SHORT).show()
//        }
//    }

}


//        if (registroProdutos != null) {
//            binding.EditProduto.text.toString().trim()
//            binding.EditQuantidade.text = registroProdutos!!.quantidade
//            binding.EditSN.text = registroProdutos!!.numeroserie
//            binding.EditPrecoCusto.text = registroProdutos!!.prcusto
//            binding.editModelo.text = registroProdutos!!.modelo
//            binding.editValorProduct.text = registroProdutos!!.valor
//
//            var save = registrolistproduto
//            save.add(registroProdutos)
//
//        } else {
//            Toast.makeText(this, "Erroa ao exibir detalhes", Toast.LENGTH_SHORT).show()
//        }


//    private fun getProdutos() {
//        FirebaseHelper
//            .getDatabase()
//            .child("regitroProdutos")
//            .child(FirebaseHelper.getUserId() ?: "")
//            .addValueEventListener(object : ValueEventListener {
//
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    if (snapshot.exists()) {
//                    }
//
//                    for (snap in snapshot.children) {
//                        val registro =
//                            snap.getValue(RegistroProdutos::class.java) as RegistroProdutos
//                        registrolistproduto.add(registro)
//                    }
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//                    Toast.makeText(this@TelaProdutoSalvo, "ERRO", Toast.LENGTH_SHORT).show()
//
//                }
//            })
//    }


//        registroProdutos = RegistroProdutos()
//        var produto = binding.EditProduto.text.toString()
//        produto = registroProdutos.produto
//
//        var quantidade = binding.EditQuantidade.text.toString()
//        produto = registroProdutos.quantidade


