package com.technosudo.httptransferer.data

data class FileData (
    val id: Int,
    val name: String,
    val path: String,
    val type: FileType
)