package com.example.head2head.domain.h2h

import com.example.head2head.data.remote.dto.h2h.H2HDto

interface H2HRemoteDataSource {
   suspend fun getRemoteData(id1: Int, id2: Int): List<H2HDto>
}