package com.gvn.osdigital.model

import android.os.Parcelable
import com.google.firebase.ktx.Firebase
import com.gvn.osdigital.helper.FirebaseHelper
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RegistroOs(


    var id: String = "",
    var nome: String = "",
    var numeroOs: String = "",
    var defeito: String = "",
    var descricao: String = "",
    var data: String = "",
    var enderecoEmpresa: String = "",
    var cep: String = "",
    var cidade: String = "",
    var bairro: String = "",
    var telefone: String = "",

    var cnpj: String = "",
    var serialNumber: String = "",
    var peca: String = "",
    var responsavel: String = "",
    var valor: String = "",

): Parcelable{
    init {
        //toda vez que adcionar ma tarefa nova com floaatactionbtn ele seta um id unico
        this.id = FirebaseHelper.getDatabase().push().key ?: ""
    }
}
