package jp.numero.android_dagashi.data

import jp.numero.android_dagashi.data.response.IndexResponse
import jp.numero.android_dagashi.data.response.MilestoneDetailResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface DagashiApi {

    @GET("api/index.json")
    suspend fun getIndex(): IndexResponse

    @GET("api/issue/{path}.json")
    suspend fun getIssue(@Path("path") path: String): MilestoneDetailResponse
}