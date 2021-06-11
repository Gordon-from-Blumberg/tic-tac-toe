package com.gordonfromblumberg.games.core.common.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileUtils {
    public static String readFile(String path) {
        String text = null;
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            StringBuilder sb = new StringBuilder();
            String ls = System.getProperty("line.separator"), line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append(ls);
            }
            text = sb.toString();
        } catch (FileNotFoundException e) {
            System.err.println("File " + path + " not found");
        } catch (IOException e) {
            System.err.println("Error during file reading");
            e.printStackTrace();
        }

        return text;
    }
}
