package com.sepanta.controlkit.netpromoterscore.service

import com.sepanta.controlkit.netpromoterscore.service.model.NetPromoterScoreResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Url

interface ApiService {
    @FormUrlEncoded
    @POST()
    suspend fun sendViewAction(
        @Url url: String,
        @Header("x-app-id") appId: String?,
        @Header("x-version") version: String,
        @Header("x-device-uuid") deviceId: String?,
        @Header("x-sdk-version") sdkVersion: String?,
        @Field("name") name: String,
    ): Response<NetPromoterScoreResponse>
    @FormUrlEncoded
    @POST()
    suspend fun sendSubmitAction(
        @Url url: String,
        @Header("x-app-id") appId: String?,
        @Header("x-version") version: String,
        @Header("x-device-uuid") deviceId: String?,
        @Header("x-sdk-version") sdkVersion: String?,
        @Field("name") name: String,
        @Field("score") score: Int,
        @Field("comment") comment: String,
    ): Response<NetPromoterScoreResponse>
}