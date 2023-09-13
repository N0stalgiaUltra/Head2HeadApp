package com.example.head2head.domain

import com.example.head2head.data.remote.dto.h2h.H2HDto

class H2HRepositoryImpl(private val h2hRemoteDataSource: H2HRemoteDataSource ): H2HRepository {
    override suspend fun getRemoteData(id1: Int, id2: Int): List<H2HDto> {
        return h2hRemoteDataSource.getRemoteData(id1, id2)
    }
}