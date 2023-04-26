package lec07

import java.io.BufferedReader
import java.io.File
import java.io.FileReader

fun readFile() {
    val currentFile = File(".")
    val file = File(currentFile.absolutePath + "/a.txt")
    val reader = BufferedReader(FileReader(file))
    for (line in reader.lines()) {
        println(line)
    }
    reader.close()
}

fun main() {
    readFile()
}