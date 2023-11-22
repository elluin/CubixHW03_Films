package com.example.cubixhw03films.data

data class Film(
    val id: String,
    val title:String,
    val description:String,
    val year: Comparable<Nothing>,
    val link: String,
    val note: String
)