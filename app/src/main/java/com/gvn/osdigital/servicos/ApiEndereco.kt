package com.gvn.osdigital.servicos

import com.gvn.osdigital.model.ViaCep
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiEndereco {

    @GET("ws/{cep}/json/")
    suspend fun recuperaEndereco(
        @Path("cep") cep: String
    ):Response<ViaCep>


}