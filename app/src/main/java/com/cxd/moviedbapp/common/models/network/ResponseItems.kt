package com.cxd.moviedbapp.common.models.network

import com.squareup.moshi.Json

data class ResponseItems<T>(
    @field:Json(name = "results") val results: List<T>
)