package com.gvn.osdigital

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.gvn.osdigital.auth.ApresentacaoFragment
import com.gvn.osdigital.databinding.ActivityDetalhesOsBinding
import com.gvn.osdigital.model.RegistroOs
import com.gvn.osdigital.model.RegistroProdutos
import com.itextpdf.text.Chunk
import com.itextpdf.text.Document
import com.itextpdf.text.Element
import com.itextpdf.text.Font
import com.itextpdf.text.FontFactory
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfTemplate
import com.itextpdf.text.pdf.PdfWriter
import com.itextpdf.text.pdf.draw.LineSeparator
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class DetalhesOsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetalhesOsBinding

    private lateinit var auth: FirebaseAuth
    //var listUser: ArrayList<Usuario> = arrayListOf()

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isAceitado ->
        if (isAceitado) Toast.makeText(this, "PERMISSÃO CONCEDIDA", Toast.LENGTH_SHORT).show()
        else Toast.makeText(this, "PERMISSÃO NEGADA", Toast.LENGTH_SHORT).show()
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetalhesOsBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        //esconder barrade status
//        window.setFlags(
//            WindowManager.LayoutParams.FLAG_FULLSCREEN,
//            WindowManager.LayoutParams.FLAG_FULLSCREEN
//        )

        //Sugestão IA pegar dados pela chave
        val dadOs = intent.getParcelableExtra<RegistroOs>("dadosOs")



        if (dadOs != null) {
            displayOsDetails(dadOs)
        } else {
            Toast.makeText(this, "Nenhum dado encontrado", Toast.LENGTH_SHORT).show()
        }



        binding.gerarPDF.setOnClickListener {

            //precisamos lidar com a permissão de tempo de execução para dispositivos com marshmallow e superior


            Toast.makeText(this, "Carregando PDF", Toast.LENGTH_SHORT).show()
            vericarPermissao(it)

        }

        binding.btExit.setOnClickListener {
            logoutUser()
        }
    }

    fun logoutUser() {
        AlertDialog.Builder(this)
            .setTitle("Sair do App OS Digital")
            .setMessage("Deseja realmente sair?")
            .setNegativeButton("NÃO") { dialog, posicao -> }
            .setPositiveButton("SIM") { dialog, posicao ->
                auth.signOut()
                startActivity(Intent(this,ApresentacaoFragment::class.java))

            }
            .create()
            .show()
    }

    private fun displayOsDetails(registroOs: RegistroOs) {
        binding.empresa.setText(registroOs.nome)
        binding.tel.setText(registroOs.telefone)
        binding.cnpj.setText(registroOs.cnpj)
        binding.nOs.setText(registroOs.numeroOs)
        binding.data.setText(registroOs.data)
        binding.ediDefeitorRel.setText(registroOs.defeito)
        binding.ediDescrico.setText(registroOs.descricao)
        binding.edResponsavel.setText(registroOs.responsavel)
        binding.ediProduto.setText(registroOs.serialNumber)
        binding.ediPecas.setText(registroOs.peca)
        binding.ediValor.setText(registroOs.valor)




    }

    private fun vericarPermissao(view: View) {
        when {
            ContextCompat.checkSelfPermission(
                this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED -> {
                Toast.makeText(this, "PERMISSÃO CONCEDIDA", Toast.LENGTH_SHORT).show()
                //generatePDF()
                val viewToConvert = findViewById<ConstraintLayout>(R.id.linearLayout)
                val pdfFileName = "documento.pdf"

                createPdfFromView(viewToConvert, pdfFileName)
                createPdfFromView(view, pdfFileName)
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) -> {
                Snackbar.make(
                    view,
                    "Esta permissão é necessaria para abrir o arquivo",
                    Snackbar.LENGTH_INDEFINITE
                )
                    .setAction("OK") {
                        requestPermissionLauncher.launch(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    }.show()
            }

            else -> {
                requestPermissionLauncher.launch(
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            }
        }
    }

    //cria pdf Ia
    fun createPdfFromView(view: View, pdfFileName: String) {


        val document = Document()
        try {

           // val pdfFileName = "/Pdf_gerado.pdf"
            val path = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),
                pdfFileName
            )
            val directory = File(Environment.getExternalStorageDirectory(), "PDF_Orcamentos")
            directory.mkdir()

            //variavel para salvar pdf com numero da O.S
            val id = binding.nOs.text
            val pdfFile = File(directory, "orcamento.pdf_$id.pdf")
            val outputStream = FileOutputStream(pdfFile)

            PdfWriter.getInstance(document, outputStream)
            document.open()


            // Adicione um título
            val title = Paragraph(" ORÇAMENTO \n\n\n", FontFactory.getFont("acme",22f))
            title.alignment = Element.ALIGN_CENTER
            document.add(title)

            // Adicione um texto
//            val text = Paragraph("Este é um exemplo de PDF criado com o iTextPDF em Kotlin.")
//            document.add(text)


            // Adicione um texto
            document.add(Paragraph("Empresa: ${binding.empresa.text}"))
            document.add(Paragraph("Telefone: ${binding.tel.text}"))
            document.add(Paragraph("CPF/CNPJ: ${binding.cnpj.text}"))
            document.add(Paragraph("N°:     ${binding.nOs.text}"))
            document.add(Paragraph("Data: ${binding.data.text}"))


            // Adicione uma linha
            val separator = LineSeparator()
            document.add(Chunk(separator))

            document.add(Paragraph("Problema Relatado: ${binding.ediDefeitorRel.text}"))

            // Adicione uma linha
            val separator2 = LineSeparator()
            document.add(Chunk(separator2))

            document.add(Paragraph("Descrição do serviço: ${binding.ediDescrico.text}"))


            // Adicione uma linha
            val separator3 = LineSeparator()
            document.add(Chunk(separator3))


            document.add(Paragraph("Produto: ${binding.ediProduto.text}"))

            // Adicione uma linha
            val separator4 = LineSeparator()
            document.add(Chunk(separator4))

            document.add(Paragraph("Peças: ${binding.ediPecas.text}"))


            // Adicione uma linha
            val separator5 = LineSeparator()
            document.add(Chunk(separator5))

            document.add(Paragraph("Valor: ${binding.ediValor.text}"))

            // Adicione uma linha
            val separator6 = LineSeparator()
            document.add(Chunk(separator6))


            document.add(Paragraph("Responsavel: ${binding.edResponsavel.text}"))

            document.add(Paragraph("Assinatura: ${binding.ed1.text}"))



            // Feche o documento
            document.close()

            // Exibindo o caminho do arquivo salvo em um Toast
            Toast.makeText(this, "PDF salvo em: ${pdfFile.absolutePath}", Toast.LENGTH_LONG).show()

            // Notifique o usuário de que o PDF foi criado
            // Você pode abrir o PDF usando um Intent aqui

        } catch (e: Exception) {

            Log.i("TAG", "Erro ao gerar", e)
            Toast.makeText(this, "Erro ao gerar PDF", Toast.LENGTH_SHORT).show()


        }





    }


//    fun compartilharPdf(context: Context) {
//        val pdfFileName = "documento.pdf"
//        val pdfFile = File(
//            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
//            pdfFileName
//        )
//
//        val intent = Intent(Intent.ACTION_SEND)
//        intent.type = "application/pdf"
//        intent.putExtra(
//            Intent.EXTRA_STREAM,
//            FileProvider.getUriForFile(context, context.packageName + ".provider", pdfFile)
//        )
//        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
//
//        startActivity(Intent.createChooser(intent, "Compartilhar PDF"))
//    }




//    private fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
//        val stream = ByteArrayOutputStream()
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
//        return stream.toByteArray()
//    }

}