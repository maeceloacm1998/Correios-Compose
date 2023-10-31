package com.puc.correios.feature.home.data.network.response

import com.google.gson.annotations.SerializedName

data class ValidateTokenResponse(
    @SerializedName("ambiente")
    val environment: String?,

    @SerializedName("id")
    val identifier: String?,

    @SerializedName("ip")
    val ipAddress: String?,

    @SerializedName("perfil")
    val profile: String?,

    @SerializedName("cpf")
    val ssn: String?,

    @SerializedName("emissao")
    val issuance: String?,

    @SerializedName("expiraEm")
    val expiration: String?,

    @SerializedName("zoneOffset")
    val timezoneOffset: String?,

    @SerializedName("token")
    val authToken: String?
)
