package com.cruning.data.rest.api.model

data class ImageDTO(
    val id: String,
    val urls: Urls,
)

data class Urls(
    val raw: String,
    val full: String,
    val regular: String,
    val small: String,
    val thumb: String,
)