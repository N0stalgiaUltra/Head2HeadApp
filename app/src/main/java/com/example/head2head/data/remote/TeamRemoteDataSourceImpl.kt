package com.example.head2head.data.remote

import android.util.Log
import com.example.head2head.data.remote.dto.TeamDto
import com.example.head2head.data.remote.response.TeamResponse
import com.example.head2head.domain.team.TeamRemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class TeamRemoteDataSourceImpl(private val api: FootballAPI): TeamRemoteDataSource {
    override suspend fun getRemoteTeams() = suspendCoroutine {
        continuation -> run{
        val call: Call<TeamResponse> = api.getTeams()
        call.enqueue(
            object: Callback<TeamResponse> {
                override fun onResponse(
                    call: Call<TeamResponse>,
                    response: Response<TeamResponse>
                ) {
                    val team = response.body()
                    if(team != null) {
                        continuation.resume(team.teamResponse.map { it.team })
                    }
                    Log.d("response", "Success")

                }

                override fun onFailure(call: Call<TeamResponse>, t: Throwable) {
                    Log.d("response", t.message ?: "")
                    continuation.resume(emptyList<TeamDto>())
                    /*TODO: Organizar tratamento de erros*/
                }
            }
        )
    }
    }
}