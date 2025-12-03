package com.example.agenda.ui

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.io.File

class FileRepository(private val context: Context) {
    val fileName = "contacts.csv"

    private fun getFile(): File {
        return File(context.filesDir, fileName)
    }

    /**
     * leo los contactos, cada linea corresponde a un contacto
     * con .split puedo separar una cadena en un array de cadenas
     * asi puedo obtener los datos separados
     */
    suspend fun readContent(): List<Contact> {
        val contactos = mutableListOf<Contact>()
        val file = getFile()

        if (file.exists()) {
            file.readLines().forEach { line ->
                val parts = line.split(",",";")
                val contact = Contact(
                    id = parts.get(0).toLong(),
                    name = parts.get(1),
                    phone = parts.get(2)
                )
                contactos.add(contact)
            }
        }
        return contactos
    }

    /**
     * escribe los datos de los contactos separados con comas
     * el Long lo paso a String
     */
    suspend fun writeLine(line: Contact) {
        val file = getFile()
        file.appendText(line.id.toString()+","+line.name+","+line.phone+ "\n")

    }
}