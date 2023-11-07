package com.gvn.osdigital.helper

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


import com.gvn.osdigital.R

class FirebaseHelper {

    companion object {

        //recupera a instance no banco de dados
        fun getDatabase() = FirebaseDatabase.getInstance().reference


        //recupera a instance do usuario que esta authenticado
        private fun getAuth() = FirebaseAuth.getInstance()

        //recupera a instance do usuario esta logado
        fun getUserId() = getAuth().uid

        fun selectId() = getDatabase().child("id")

        //Verifica se o usuario já esta logado no app
        fun checKLoggedUser() = getAuth().currentUser != null

        //validaçao de erro do firebase
        fun validError(error: String): Int {
            return when {
                error.contains("There is no user record corresponding to this identifier") -> {
                    R.string.account_not_registered_register_fragment
                }

                error.contains("The email address is badly formatted") -> {
                    R.string.invalid_email_register_fragment

                }

                error.contains("The password is invalid or the user does not have a password") -> {
                    R.string.invalid_password_register_fragment
                }

                error.contains("The email address is already in use by another account") -> {
                    R.string.email_in_use_register_fragment
                }

                error.contains("The given password is invalid. [ Password should be at least 6 characters ]") -> {
                    R.string.strong_password_register_fragment

                }

                else -> {
                    R.string.erro_generic
                }
            }
        }

    }

}