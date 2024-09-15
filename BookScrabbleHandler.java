package test;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class BookScrabbleHandler implements ClientHandler {
    private final DictionaryManager dictionaryManager;

    public BookScrabbleHandler() {
        this.dictionaryManager = DictionaryManager.get();
    }

    @Override
    public void handleClient(InputStream inFromClient, OutputStream outToClient) {
        try (Scanner in = new Scanner(inFromClient)) {
            PrintWriter out = new PrintWriter(outToClient);
            String userInput = in.nextLine();
            String[] splittedInput = userInput.split(",");
            String action = splittedInput[0];
            String[] fileNamesWithWord = new String[splittedInput.length - 1];
            System.arraycopy(splittedInput, 1, fileNamesWithWord, 0, fileNamesWithWord.length);

            boolean res = false;

            if(action.equals("Q")) {
                res = this.dictionaryManager.query(fileNamesWithWord);
            }
            else if(action.equals("C")) {
                res = this.dictionaryManager.challenge(fileNamesWithWord);
            }

            out.println(res + "\n");
            out.flush();
        }
    }

    @Override
    public void close() {}
}