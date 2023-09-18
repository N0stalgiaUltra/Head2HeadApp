package com.n0stalgiaultra.head2head.domain.h2h

import com.n0stalgiaultra.head2head.data.remote.dto.h2h.H2HDto

interface H2HRepository {
    suspend fun getRemoteData(id1: Int, id2: Int): List<H2HDto>
}