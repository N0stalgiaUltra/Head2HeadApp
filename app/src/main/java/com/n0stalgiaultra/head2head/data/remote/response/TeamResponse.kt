package com.n0stalgiaultra.head2head.data.remote.response

import com.google.gson.annotations.SerializedName

data class TeamResponse(
    @SerializedName("response")
    val teamResponse: List<TeamResponseItem>
)
