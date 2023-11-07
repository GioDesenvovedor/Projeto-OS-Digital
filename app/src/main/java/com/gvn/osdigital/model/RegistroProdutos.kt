package com.gvn.osdigital.model

import android.os.Parcelable
import com.gvn.osdigital.helper.FirebaseHelper
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RegistroProdutos (
    var id: String = "",
    var produto: String = "",
    var valor: String = "",
    var modelo: String = "",
    var numeroserie: String = "",
    var quantidade: String = "",
    var prcusto: String = "",
): Parcelable {
    init {
        //toda vez que adcionar ma tarefa nova com floaatactionbtn ele seta um id unico
        this.id = FirebaseHelper.getDatabase().push().key ?: ""
    }
}