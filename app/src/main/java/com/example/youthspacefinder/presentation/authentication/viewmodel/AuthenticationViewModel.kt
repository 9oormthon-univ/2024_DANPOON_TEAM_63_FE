package com.example.youthspacefinder.presentation.authentication.viewmodel

import androidx.lifecycle.ViewModel

class AuthenticationViewModel: ViewModel() {
    var id: String = ""
    var password: String = ""
    var isUserLoggedIn: Boolean = false
    var userToken: String? = null
}