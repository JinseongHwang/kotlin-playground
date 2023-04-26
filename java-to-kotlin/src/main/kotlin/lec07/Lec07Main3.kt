package lec07

import java.io.BufferedReader
import java.io.File
import java.io.FileReader

fun readFile(path: String) {
    BufferedReader(FileReader(path)).use { reader ->
        for (line in reader.lines()) {
            println(line)
        }
    }
}

fun main() {
    val currentFile = File(".")
    val file = File(currentFile.absolutePath + "/a.txt")
    readFile(file.absolutePath)
}