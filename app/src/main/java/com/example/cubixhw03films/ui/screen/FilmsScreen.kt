package com.example.cubixhw03films.ui.screen

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.cubixhw03films.data.Film
import java.util.UUID


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilmsScreen(
    filmsViewModel: FilmsViewModel = viewModel(),
    navController: NavController
) {

    var showAddFilmDialog by rememberSaveable {
        mutableStateOf(false)
    }
    var expanded by remember { mutableStateOf(false) }

    var editFilm: Film? by rememberSaveable {
        mutableStateOf(null)
    }

    var filmToEdit: Film? by rememberSaveable {
        mutableStateOf(null)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Films") },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(imageVector = Icons.Default.Search, contentDescription = null)
                    }
                    IconButton(onClick = {}) {
                        Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
                    }
                }
            )
        },

        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    showAddFilmDialog = true
                },
                modifier = Modifier.padding(16.dp),
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        content = { innerPadding ->
            Column(
                modifier = Modifier.padding(innerPadding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Content")

                if (showAddFilmDialog) {
                    FilmForm(
                        onDialogClose = {
                            showAddFilmDialog = false
                        },
                        filmsViewModel = filmsViewModel,
                        filmToEdit = filmToEdit
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (filmsViewModel.getAllFilm().isEmpty())
                    Text(text = "No items")
                else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(16.dp)
                    ) {
                        items(filmsViewModel.getAllFilm()) {
                            FilmCard(it)
                        }
                    }
                }
            }
        }

    )
}

@Composable
fun FilmCard(
    film: Film,
    onRemoveItem: () -> Unit = {}
) {
    val context = LocalContext.current
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onSecondary,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = film.title,
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.weight(1f)
            )

            Row {
                IconButton(
                    onClick = { /* Szerkesztés eseménykezelő */ },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Szerkesztés",
                        tint = Color.DarkGray
                    )
                }
                IconButton(
                    onClick = { onRemoveItem() },
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            onRemoveItem()
                        }
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Törlés",
                        tint = Color.DarkGray
                    )
                }
            }
        }

        Text(
            text = "${film.year}",
            style = TextStyle(fontSize = 14.sp),
            modifier = Modifier.padding(8.dp)
        )

        Text(
            text = film.description,
            style = TextStyle(fontSize = 16.sp),
            modifier = Modifier.padding(8.dp)
        )

        Text(
            text = film.link,
            style = TextStyle(
                fontSize = 14.sp,
                color = Color.Blue,
                textDecoration = TextDecoration.Underline,
            ),
            modifier = Modifier
                .padding(8.dp)
                .clickable {
                    val uri = Uri.parse(film.link)
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    context.startActivity(intent)
                }
        )

        Spacer(modifier = Modifier.height(8.dp))
        // Rejtett mező
        Text(
            text = "Film azonosító: ${film.id}",
            style = TextStyle(fontSize = 14.sp),
            color = Color.Transparent
        )

        Spacer(modifier = Modifier.height(8.dp))
    }
}


//CARD ------------------------------------------------------------------------------------------

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilmForm(
    filmsViewModel: FilmsViewModel = viewModel(),
    onDialogClose: () -> Unit = {},
    filmToEdit: Film? = null
) {
    var newFilmTitle by remember { mutableStateOf(filmToEdit?.title ?: "") }
    var newFilmDesc by remember { mutableStateOf(filmToEdit?.description ?: "") }
    var newFilmLink by remember { mutableStateOf(filmToEdit?.link ?: "") }
    var newFilmNote by remember { mutableStateOf(filmToEdit?.note ?: "") }
    var newFilmYear by remember { mutableStateOf(filmToEdit?.year ?: "") }

    val isTitleEmpty = newFilmTitle.isBlank()

    Dialog(onDismissRequest = onDialogClose) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(size = 6.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = newFilmTitle,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    label = { Text(text = "Film title") },
                    onValueChange = { newFilmTitle = it }
                )

                OutlinedTextField(
                    value = newFilmDesc,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    label = { Text(text = "Description") },
                    onValueChange = { newFilmDesc = it }
                )

                OutlinedTextField(
                    value = newFilmNote,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    label = { Text(text = "Film note") },
                    onValueChange = { newFilmNote = it }
                )

                OutlinedTextField(
                    value = newFilmLink,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    label = { Text(text = "IMDB link") },
                    onValueChange = { newFilmLink = it }
                )

                OutlinedTextField(
                    value = newFilmYear.toString(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    label = { Text(text = "Year") },
                    onValueChange = {
                        newFilmYear = it.toIntOrNull() ?: 0
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        if (!isTitleEmpty) {
                            if (filmToEdit == null) {
                                filmsViewModel.addFilm(
                                    Film(
                                        id = UUID.randomUUID().toString(),
                                        title = newFilmTitle,
                                        description = newFilmDesc,
                                        note = newFilmNote,
                                        year = newFilmYear,
                                        link = newFilmLink
                                    )
                                )
                            } else {
                                var filmEdited = filmToEdit.copy(
                                    title = newFilmTitle,
                                    description = newFilmDesc,
                                    link = newFilmLink,
                                    year = newFilmYear
                                )

                                filmsViewModel.editFilm(filmToEdit, filmEdited)
                            }

                            onDialogClose()
                        }
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(text = "Save")
                }
            }
        }
    }
}
