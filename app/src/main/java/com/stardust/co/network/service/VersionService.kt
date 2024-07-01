package com.stardust.co.network.service


import com.stardust.co.model.Version
import com.stardust.co.network.ApiResponse

import retrofit2.http.GET

interface VersionService {
    companion object{
        const val SERVER_URL = "http://www.smallfurrypaw.top/"
    }
    @GET("files/co-version.json")
    suspend fun getVersion(): ApiResponse<Version>
}