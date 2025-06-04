package com.shrivatsa.matcher;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
public class ResumeParser {
 public static String parseResume(String filePath) {
        try {
            return new String(Files.readAllBytes(Paths.get(filePath)))
                    .replaceAll("[^a-zA-Z0-9\\s]", "") // remove punctuation
                    .toLowerCase(); // normalize case
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
