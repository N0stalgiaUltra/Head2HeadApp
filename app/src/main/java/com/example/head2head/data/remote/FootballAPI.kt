package com.example.head2head.data.remote

import com.example.head2head.data.remote.response.HTHResponse
import com.example.head2head.data.remote.response.TeamResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FootballAPI {

    @GET("teams")
    fun getTeams(
        @Query("league") league: String = LEAGUE_ID,
        @Query("season") season: String = LEAGUE_SEASON
    ): Call<TeamResponse>

    @GET("fixtures/headtohead")
    fun getH2H(
        @Query("h2h") teams: String
    ): Call<HTHResponse>

    companion object{
        val BASE_URL = "https://v3.football.api-sports.io/"
        val API_KEY = "x-rapidapi-key"
        val API_KEY_VALUE = "454c3df678ac9d84ff02fef28f33994d"
        val LEAGUE_ID = "71"
        val LEAGUE_SEASON = "2023"
    }
}
