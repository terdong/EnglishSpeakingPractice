package com.teamgehem.englishspeakingpractice.network

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.teamgehem.englishspeakingpractice.LogTitle
import com.teamgehem.englishspeakingpractice.LogTitleError
import org.json.JSONObject

object VolleyHelper {

    private val url = "https://script.google.com/macros/s/AKfycbyXSCGdKWXKA2abHGpMiDTryWg0SorY_A1r6BhJVH5EH-E716w/exec?cmd="
    private val url2 = "https://script.google.com/macros/s/AKfycbyXSCGdKWXKA2abHGpMiDTryWg0SorY_A1r6BhJVH5EH-E716w/exec?cmd=%s&data=%s"

    val defaultResponseListener: (JSONObject) -> Unit = { jsonObject ->
        Log.d(LogTitle, "서버 response 수신: $jsonObject")
    }


    fun request(context: Context, cmd: DbCommand, responseListener: (JSONObject) -> Unit = defaultResponseListener) {
        Volley.newRequestQueue(context).add(JsonObjectRequest(Request.Method.GET, "$url${cmd.name}", null, responseListener, { error -> Log.d(LogTitleError, "서버 Response 가져오기 실패: $error") }))
    }

    fun request(context: Context, cmd: DbCommand, localDate: Long, responseListener: (JSONObject) -> Unit = defaultResponseListener) {
        Volley.newRequestQueue(context).add(JsonObjectRequest(Request.Method.GET, url2.format(cmd.name, localDate), null, responseListener, { error -> Log.d(LogTitleError, "서버 Response 가져오기 실패: $error") }))
    }
}