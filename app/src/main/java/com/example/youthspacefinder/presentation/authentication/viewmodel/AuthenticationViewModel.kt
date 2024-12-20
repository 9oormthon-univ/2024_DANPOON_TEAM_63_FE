package com.example.youthspacefinder.presentation.authentication.viewmodel

import androidx.lifecycle.ViewModel

class AuthenticationViewModel: ViewModel() {
    var id: String = ""
    var password: String = ""
    var nickname: String = ""
    var email: String = ""
    var isUserLoggedIn: Boolean = false
    var userToken: String? = null
}