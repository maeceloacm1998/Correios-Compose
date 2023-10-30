package com.puc.correios.feature.login.data.network.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("success")
    val success: Int
)