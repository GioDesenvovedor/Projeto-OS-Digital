package com.gvn.osdigital.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import com.gvn.osdigital.databinding.FragmentCriaOsBinding
import com.gvn.osdigital.helper.FirebaseHelper
import com.gvn.osdigital.helper.FirestoreHelper
import com.gvn.osdigital.model.RegistroOs
import com.gvn.osdigital.model.ViaCep
import com.gvn.osdigital.servicos.ApiEndereco
import com.gvn.osdigital.servicos.RetrofitHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.create
import java.text.SimpleDateFormat
import java.util.Calendar


class CriaOsFragment : Fragment(), DatePickerDialog.OnDateSetListener {

    private var _binding: FragmentCriaOsBinding? = null
    private val binding get() = _binding!!

    // formatar para data
    private val dateformat: SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
    private lateinit var registroOs: RegistroOs
    private val firestore by lazy {
        FirestoreHelper()
    }
    private val retrofit by lazy {
        RetrofitHelper.retrofit
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCriaOsBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        buscarCep()
        clickDate()
        clickBtn()

    }

    private fun buscarCep(){

        binding.btnBuscarCep.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                buscarEndereco()
            }
        }
    }

    /// acessar a interface da Api
    private suspend fun buscarEndereco() {

        var retorno: Response<ViaCep>? = null

        var cepDigitado = binding.editBuscarCep.text.toString().trim()
        try {
            val enderecoApi = retrofit.create(ApiEndereco::class.java)
            retorno = enderecoApi.recuperaEndereco(cepDigitado)


        }catch (e: Exception){
            e.printStackTrace()

            Log.i("TG", "Erro: - ${retorno?.code()}")
        }
        if (retorno != null){
            if (retorno.isSuccessful){
                val viaCep = retorno.body()
                withContext(Dispatchers.Main){

                    val endereco = viaCep?.logradouro.toString()
                    binding.editEndereco.setText(endereco)

                    val bairro = viaCep?.bairro.toString()
                    binding.editBairro.setText(bairro)

                    val cidade = viaCep?.localidade.toString()
                    binding.editCidade.setText(cidade)
                }



            }
        }else {
            withContext(Dispatchers.Main) {
                Log.i("tag", "ERRO CODE: ${retorno?.code()}")
            }
        }
    }

    private fun clickDate() {
        binding.EditDataOs.setOnClickListener {
            handleDate()
        }
    }

    private fun clickBtn() {
        binding.btnSalvarOs.setOnClickListener {
            validation()

        }

    }

    private fun validation() {
        val nome = binding.EditNomEmpresa.text.toString().trim()
        val defeito = binding.EditDefeitorelatado.text.toString().trim()
        val endereco = binding.editEndereco.text.toString().trim()
        val cnpj = binding.editCnpj.text.toString().trim()
        val cep = binding.editBuscarCep.text.toString().trim()
        val bairro = binding.editBairro.text.toString().trim()
        val cidade = binding.editCidade.text.toString().trim()
        val telefone = binding.editTelefone.text.toString().trim()
        val descricao = binding.EditRelatorioecnic.text.toString().trim()
        val serialN = binding.EditSerialNumberProduct.text.toString().trim()
        val peca = binding.EditPeca.text.toString().trim()
        val responsavel = binding.EditResponsavel.text.toString().trim()
        val data = binding.EditDataOs.text.toString().trim()
        val numeroOs = binding.EditNumbOs.text.toString().trim()
        val valor = binding.EditValor.text.toString().trim()

        if (nome.isNotEmpty() && defeito.isNotEmpty() && data.isNotEmpty()
            && numeroOs.isNotEmpty()
        ) {

            binding.progresBar.isVisible = true
            registroOs = RegistroOs()
            registroOs.numeroOs = numeroOs
            registroOs.data = data
            registroOs.defeito = defeito
            registroOs.nome = nome
            registroOs.cnpj = cnpj
            registroOs.valor = valor
            registroOs.enderecoEmpresa = endereco
            registroOs.cep = cep
            registroOs.bairro = bairro
            registroOs.cidade = cidade
            registroOs.telefone = telefone
            registroOs.descricao = descricao
            registroOs.serialNumber = serialN
            registroOs.peca = peca
            registroOs.responsavel = responsavel

            saveOs()

        } else {
            Toast.makeText(
                requireContext(),
                "Os campos: NOME DA EMPRESA, DEFEITO, DATA E NÚMERO DA OS, não podem ficar em branco!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }


    private fun saveOs() {
        FirebaseHelper.getDatabase()
            .child("registroOs")
            .child(FirebaseHelper.getUserId() ?: "")
            .child(registroOs.id)
            .setValue(registroOs)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    findNavController().popBackStack()
                    Toast.makeText(requireContext(), "Salvo com sucesso !", Toast.LENGTH_SHORT)
                        .show()
                } else {

                    Toast.makeText(requireContext(), "Erro ao salvar Os !", Toast.LENGTH_SHORT)
                        .show()
                }
            }.addOnFailureListener {
                binding.progresBar.isVisible = false
                Toast.makeText(requireContext(), "Erro ao salvar Os 2!", Toast.LENGTH_SHORT).show()
            }

    }

//    private fun salvardados() {
//
//
//        firestore.getDatabase()
//            .collection("osRegistrada")
//            .document(firestore.getUserId() ?: "")
//            .collection("osRegistrada")
//            .document(registroOs.id)
//            .set(registroOs)
//            .addOnCompleteListener { resultado ->
//                if (resultado.isSuccessful) {
//                    findNavController().popBackStack()
//                    Toast.makeText(requireContext(), "Salvo com sucesso !", Toast.LENGTH_SHORT)
//                        .show()
//                } else {
//
//                    Toast.makeText(requireContext(), "Erro ao salvar Os !", Toast.LENGTH_SHORT)
//                        .show()
//                }
//            }.addOnFailureListener {
//                binding.progresBar.isVisible = false
//                Toast.makeText(requireContext(), "Erro ao salvar Os 2!", Toast.LENGTH_SHORT).show()
//            }
//
//    }



    override fun onDateSet(v: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)


        val editDate = dateformat.format(calendar.time)

        binding.EditDataOs.text = editDate

    }

    private fun handleDate() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(requireContext(), this, year, month, day).show()


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}