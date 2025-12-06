package com.example.agenda.model.repository

import android.content.Context
import com.example.agenda.model.data.Contact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class ContactFileRepository (private val context: Context){
    val fileName = "contacts.csv"

    private fun getFile(): File {
        return File(context.filesDir, fileName)
    }

    /**
     * suspend -> corrutinas
     * funciones en segundo plano para no afectar a la interfaz de usuario
     */
    suspend fun readContacts(): List<Contact> {
        //IO para ejecutar corrutinas de entrada/salida
        //debe ir dentro del return para que se ejecute como corrutina IO
        return withContext(Dispatchers.IO) {
            //AQUI DENTRO VA LA LOGICA
            val contactos = mutableListOf<Contact>()
            val file = getFile()

            if (file.exists()) {
                file.readLines().forEach { line ->
                    val parts = line.split(",",";")
                    val contact = Contact(
                        id = parts.get(0).toInt(),
                        name = parts.get(1),
                        phoneNumber = parts.get(2)
                    )
                    contactos.add(contact)
                }
            }
            contactos
        }
    }

    suspend fun writeContact(line: Contact) {
        return withContext(Dispatchers.IO){
            val file = getFile()
            file.appendText(line.id.toString()+","+line.name+","+line.phoneNumber+ "\n")
        }
    }

    suspend fun editContact(editContact: Contact) {
        withContext(Dispatchers.IO) {
            val contactos = readContacts().toMutableList()

            val index = contactos.indexOfFirst { it.id == editContact.id }

            if (index != -1) {
                contactos[index] = editContact

                //Reescribimos el archivo
                val file = getFile()
                file.writeText("")

                contactos.forEach { contact ->
                    file.appendText(
                        contact.id.toString() + "," +
                                contact.name + "," +
                                contact.phoneNumber + "\n"
                    )
                }
            }
        }
    }

    suspend fun deleteContact(deleteContact: Contact) {
        withContext(Dispatchers.IO) {

            val contactos = readContacts().toMutableList()
            val nuevaLista = mutableListOf<Contact>() //con los contactos != deleteContac.id

            for (contact in contactos) {
                if (contact.id != deleteContact.id) {
                    nuevaLista.add(contact)
                }
            }

            // Reescribimos el archivo
            val file = getFile()
            file.writeText("")

            for (contact in nuevaLista) {
                file.appendText(
                    contact.id.toString() + "," +
                            contact.name + "," +
                            contact.phoneNumber + "\n"
                )
            }
        }
    }

}