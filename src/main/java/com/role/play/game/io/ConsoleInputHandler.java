package com.role.play.game.io;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Responsible for reading input from the console
 *
 * @author ddigges
 */
public class ConsoleInputHandler {
    private BufferedReader reader;

    public ConsoleInputHandler(BufferedReader reader) {
        this.reader = reader;
    }

    /**
     * Reads and returns the next line of input
     * @return
     */
    public String readInput() {
        try{
            return reader.readLine();
        } catch (IOException ex) {
            ex.printStackTrace();
            return  null;
        }
    }
}
