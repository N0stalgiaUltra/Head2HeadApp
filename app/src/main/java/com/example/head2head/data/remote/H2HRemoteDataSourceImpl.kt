package com.example.head2head.data.remote

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.head2head.data.remote.dto.h2h.H2HDto
import com.example.head2head.data.remote.response.HTHResponse
import com.example.head2head.domain.H2HRemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class H2HRemoteDataSourceImpl( private val api: FootballAPI ): H2HRemoteDataSource{

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