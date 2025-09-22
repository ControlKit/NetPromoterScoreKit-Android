package com.sepanta.controlkit.netpromoterscore.service

import com.sepanta.controlkit.netpromoterscore.service.apierror.NetworkResult
import com.sepanta.controlkit.netpromoterscore.service.apierror.handleApi
import com.sepanta.controlkit.netpromoterscore.service.model.NetPromoterScoreResponse


class NetPromoterScoreApi(private val apiService: ApiService) {

    suspend fun sendViewAction(
        route: String, appId: String, version: String, deviceId: String,
        sdkVersion: String,
        name: String,
    ): NetworkResult<NetPromoterScoreResponse> {
        return handleApi {
            apiService.sendViewAction(
                route,
                appId,
                version,
                deviceId,
                sdkVersion,
                name = name,
            )
        }
    }
    suspend fun sendSubmitAction(
        route: String, appId: String, version: String, deviceId: String,
        sdkVersion: String,
        name: String,
        score: Int,
        comment: String
    ): NetworkResult<NetPromoterScoreResponse> {
        return handleApi {
            apiService.sendSubmitAction(
                route,
                appId,
                version,
                deviceId,
                sdkVersion,
                name = name,
                score = score,
                comment = comment
            )
        }
    }
}
