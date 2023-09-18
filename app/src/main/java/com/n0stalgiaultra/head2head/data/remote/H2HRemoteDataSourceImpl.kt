package com.n0stalgiaultra.head2head.data.remote

import android.util.Log
import com.n0stalgiaultra.head2head.data.remote.dto.h2h.H2HDto
import com.n0stalgiaultra.head2head.data.remote.response.HTHResponse
import com.n0stalgiaultra.head2head.domain.h2h.H2HRemoteDataSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class H2HRemoteDataSourceImpl( private val api: FootballAPI ): H2HRemoteDataSource {

    override suspend fun getRemoteData(id1: Int, id2: Int): List<H2HDto> =
        suspendCoroutine{
            continuation -> run{
                val teams: String = "${id1}-${id2}"
                val call: Call<HTHResponse> = api.getH2H(teams)
                call.enqueue(
                    object: Callback<HTHResponse> {
                        override fun onResponse(
                            call: Call<HTHResponse>,
                            response: Response<HTHResponse>
                        ) {
                            val data = response.body()

                            if(data != null) {
                               continuation.resume(data.h2hResponse)

                            }

                            Log.d("response", "Success")

                        }

                        override fun onFailure(call: Call<HTHResponse>, t: Throwable) {
                            Log.d("response", t.message ?: "")
                            continuation.resume(emptyList<H2HDto>())
                        }
                    }
                )
            }
        }
}