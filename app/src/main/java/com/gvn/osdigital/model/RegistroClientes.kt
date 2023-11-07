package com.gvn.osdigital.model

import android.os.Parcelable
import com.gvn.osdigital.helper.FirebaseHelper
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RegistroClientes(

    var id: String = "",
    var nomeEmpresa: String = "",
    var nomeResponsavel: String = "",
    var email: String = "",
    var endEmpresa: String = "",
    var telefone: String = "",
    var numero: String = "",
    var cep: String = "",
    var cnpj: String = "",
) : Parcelable {
    init {
        //toda vez que adcionar uma tarefa nova com floaatactionbtn ele seta um id unico
        this.id = FirebaseHelper.getDatabase().push().key ?: ""
    }
}
