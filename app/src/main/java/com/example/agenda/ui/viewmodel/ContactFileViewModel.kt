package com.example.agenda.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agenda.model.data.Contact
import com.example.agenda.model.repository.ContactFileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ContactFileViewModel (private val repository: ContactFileRepository) : ViewModel() {

    private val _contacts = MutableStateFlow<List<Contact>>(emptyList())
    val contacts = _contacts.asStateFlow() //hacemos una copia PUBLICA para que la vea Compose

    fun readContacts () {
        viewModelScope.launch {
            _contacts.value = repository.readContacts()
        }
    }
}