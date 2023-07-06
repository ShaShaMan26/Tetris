package Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String localPath = System.getenv("APPDATA");
        String savePath = localPath + "\\Sha's-Tetris\\SaveData.txt";

        new File(localPath + "\\Sha's-Tetris").mkdir();
        try {
            new File(localPath + "\\Sha's-Tetris\\SaveData.txt").createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        boolean hardDropEnabled = false;
        boolean ghostEnabled = false;
        boolean fullscreen = false;
        try {
            Scanner saveDataScanner = new Scanner(new File(savePath));
            if (saveDataScanner.nextInt() > 0) {
                hardDropEnabled = true;
            }

            if (saveDataScanner.nextInt() > 0) {
                ghostEnabled = true;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (NoSuchElementException e) {
            try {
                FileWriter fileWriter = new FileWriter(savePath);
                fileWriter.write("0\n0");
                fileWriter.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

        new Instance(hardDropEnabled, ghostEnabled, fullscreen).run();
    }
}
