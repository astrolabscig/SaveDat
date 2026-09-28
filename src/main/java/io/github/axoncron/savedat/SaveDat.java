package io.github.axoncron.savedat;

import java.nio.file.Files;
import java.nio.file.Path; 

public class SaveDat {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Usage: savedat <path> [<path> ...]");
            System.exit(1);
        } 

        boolean allDirectories = true;

        for (String argument : args) {
            String verdict = "error";
            try {
                Path path = Path.of(argument);
                
                if (Files.exists(path)) {
                    if (Files.isDirectory(path)){
                        verdict = "directory"; 
                    } else {
                        verdict = "not a directory";
                        allDirectories = false;
                    }
                }else if (Files.notExists(path)) {
                    verdict = "does not exist";
                    allDirectories = false;
                } else {
                    verdict = "unknown";
                    allDirectories = false;
                }
                System.out.println(argument + ": [ " + verdict + " ]");

            } catch ( java.nio.file.InvalidPathException e) {
                System.out.println(argument + ":  [ invalid path: " + e.getMessage() + " ]");
                allDirectories = false;
            }
        }
        if (allDirectories) {
            System.exit(0);
        } else {
            System.exit(1);
        }
    }
}
