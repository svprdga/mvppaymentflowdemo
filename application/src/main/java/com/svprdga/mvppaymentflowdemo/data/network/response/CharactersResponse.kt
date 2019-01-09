package com.svprdga.mvppaymentflowdemo.data.network.response

data class CharactersResponse(
    val code: Int,
    val status: String,
    val data: DataEntity)

data class DataEntity(
    val results: List<CharacterEntity>
)

data class Thumbnail(
    val path: String,
    val extension: String
)

data class CharacterEntity(
    val id: Int,
    val name: String,
    val thumbnail: Thumbnail
)