package com.n0stalgiaultra.head2head.data.remote.dto.h2h

data class FixtureDto(
    val date: String,
    val venue: VenueDto
)

data class VenueDto(
    val name: String?,
)