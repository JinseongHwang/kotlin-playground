package lec07;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class JavaFilePrinter {

    public void readFile(String path) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            reader.lines().forEach(System.out::println);
        }
    }

    public static void main(String[] args) throws IOException {
        final JavaFilePrinter javaFilePrinter = new JavaFilePrinter();
        final File currentFile = new File(".");
        final File file = new File(currentFile.getAbsolutePath() + "/a.txt");
        javaFilePrinter.readFile(file.getAbsolutePath());
    }

}
