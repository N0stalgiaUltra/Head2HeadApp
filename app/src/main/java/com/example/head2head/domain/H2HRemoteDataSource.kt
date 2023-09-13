package com.example.head2head.domain

import com.example.head2head.data.remote.dto.h2h.H2HDto

interface H2HRemoteDataSource {
   suspend fun getRemoteData(id1: Int, id2: Int): List<H2HDto>
}