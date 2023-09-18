package com.n0stalgiaultra.head2head.data.remote

import com.n0stalgiaultra.head2head.data.remote.dto.h2h.H2HDto
import com.n0stalgiaultra.head2head.domain.h2h.H2HRemoteDataSource
import com.n0stalgiaultra.head2head.domain.h2h.H2HRepository

class H2HRepositoryImpl(private val h2hRemoteDataSource: H2HRemoteDataSource): H2HRepository {
    override suspend fun getRemoteData(id1: Int, id2: Int): List<H2HDto> {
        return h2hRemoteDataSource.getRemoteData(id1, id2)
    }
}