package com.cxd.moviedbapp.common.models.network

import com.squareup.moshi.Json

data class ResponseError(
    @field:Json(name = "status_message") val message: String,
    @field:Json(name = "status_code") val status: Int
)