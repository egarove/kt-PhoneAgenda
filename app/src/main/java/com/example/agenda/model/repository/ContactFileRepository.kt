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
                        phone = parts.get(2)
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
            file.appendText(line.id.toString()+","+line.name+","+line.phone+ "\n")
        }
    }
   /*
    suspend fun editContact(contact: Contact) {
        return withContext(Dispatchers.IO) {
            delay(1000)
            val file = getFile()
            file.appendText(line + "\n")
        }
    }

    /*
    suspend fun deleteContact(contact: Contact) {
        return withContext(Dispatchers.IO) {
            delay(1000)
            val file = getFile()
            file.appendText(line + "\n")
        }
    }

     */*/
}