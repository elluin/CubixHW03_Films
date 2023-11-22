package com.example.cubixhw03films.ui.screen

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.cubixhw03films.data.Film

class FilmsViewModel : ViewModel() {
    private var _filmList =
        mutableStateListOf(
            Film(
                id = "1",
                title = "Film címe 1",
                description = "Film leírása 1",
                year = 2021,
                link = "https://www.imdb.com/film1",
                note = "Megjegyzés 1"
            ),
            Film(
                id = "2",
                title = "Film címe 2",
                description = "Film leírása 2",
                year = 2022,
                link = "https://www.imdb.com/film2",
                note = "Megjegyzés 2"
            ),
            Film(
                id = "2",
                title = "Film címe 2",
                description = "Film leírása 2",
                year = 2022,
                link = "https://www.imdb.com/film2",
                note = "Megjegyzés 2"
            ),
            Film(
                id = "2",
                title = "Film címe 2",
                description = "Film leírása 2",
                year = 2022,
                link = "https://www.imdb.com/film2",
                note = "Megjegyzés 2"
            )
        )

    fun getAllFilm(): List<Film> {
        return _filmList;
    }

    fun addFilm(filmItem: Film) {
        _filmList.add(filmItem)
    }

    fun editFilm(originalFilm: Film, editedFilm: Film) {
        val index = _filmList.indexOf(originalFilm)
        _filmList[index] = editedFilm
    }

    fun removeFilm(filmItem: Film) {
        _filmList.remove(filmItem)
    }

}