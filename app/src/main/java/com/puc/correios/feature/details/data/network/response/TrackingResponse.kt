package com.puc.correios.feature.details.data.network.response

import com.google.gson.annotations.SerializedName

data class TrackingResponse(
    @SerializedName("codigo") val code: String,
    @SerializedName("host") val host: String,
    @SerializedName("eventos") val events: List<Event>,
    @SerializedName("time") val time: Double,
    @SerializedName("quantidade") val quantity: Int,
    @SerializedName("servico") val service: String,
    @SerializedName("ultimo") val lastUpdated: String
)

data class Event(
    @SerializedName("data") val date: String,
    @SerializedName("hora") val time: String,
    @SerializedName("local") val location: String,
    @SerializedName("status") val status: String,
    @SerializedName("subStatus") val subStatus: List<String>
)