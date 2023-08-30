package com.example.head2head.data.remote.response

import com.google.gson.annotations.SerializedName

data class HTHResponse(
    @SerializedName("response")
    val h2hResponse: List<HTHResponse>
)
