package com.n0stalgiaultra.head2head.data.remote.response

import com.n0stalgiaultra.head2head.data.remote.dto.h2h.H2HDto
import com.google.gson.annotations.SerializedName

data class HTHResponse(
    @SerializedName("response")
    val h2hResponse: List<H2HDto>
)

