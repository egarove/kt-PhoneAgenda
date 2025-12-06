package com.example.agenda.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.agenda.model.data.Contact
import com.example.agenda.model.repository.ContactFileRepository
import com.example.agenda.ui.viewmodel.ContactFileViewModel
import kotlinx.coroutines.launch

@Composable
fun Item(
    navController: NavController,
    contact: Contact,
    repository: ContactFileRepository
) {

    val scope = rememberCoroutineScope() //para poder lanzar las corrutinas

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        onClick = { navController.navigate("edit-contact/${contact.id}") }
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Mostramos nombre y teléfono
            Text(text = "${contact.name} ${contact.phoneNumber}")

            Row {
                // Botón Delete
                Button(
                    onClick = {
                        scope.launch {
                            repository.deleteContact(contact)
                        }
                        navController.navigate("home-screen")
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("Delete")
                }

                // Botón Edit
                Button(
                    onClick = { navController.navigate("edit-contact/${contact.id}") },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text("Edit")
                }
            }
        }
    }
}