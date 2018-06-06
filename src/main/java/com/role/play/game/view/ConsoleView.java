package com.role.play.game.view;

import com.role.play.game.io.ConsoleInputHandler;

/**
 * A console view that has methods to read from the console and display onto the
 * console
 *
 * @author ddigges
 */
public class ConsoleView implements View {
    private ConsoleInputHandler consoleInputHandler;

    public ConsoleView(ConsoleInputHandler consoleInputHandler) {
        this.consoleInputHandler = consoleInputHandler;
    }

    public void writeOutput(String output) {
        System.out.println(output);
    }

    public String readInput() {
        return consoleInputHandler.readInput();
    }
}
