package com.example.agenda.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import com.example.agenda.model.data.Contact
import com.example.agenda.model.repository.ContactFileRepository
import com.example.agenda.ui.viewmodel.ContactFileViewModel


@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: ContactFileViewModel,
    innerPadding: PaddingValues
) {

    LaunchedEffect(Unit) {
        viewModel.readContacts()
    }

    val contacts = viewModel.contacts.collectAsState()
    val context = LocalContext.current
    val repository = ContactFileRepository(context)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        if (contacts.value.isEmpty()) {
            Text("No contacts found.")
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                items(items = contacts.value) { contact ->
                    Item(
                        navController = navController,
                        contact = contact,
                        repository
                    )
                }
            }
        }

        Button(onClick = { navController.navigate("add-contact") }) {
            Text("ADD Contact")
        }
    }
}