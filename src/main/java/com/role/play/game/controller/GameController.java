package com.role.play.game.controller;

import java.text.MessageFormat;

import com.role.play.game.model.GameEngine;
import com.role.play.game.model.entities.Menu;
import com.role.play.game.view.View;

import static com.role.play.game.common.Constants.PERIOD;
import static com.role.play.game.common.Constants.SPACE;

/**
 * Controls the interaction between the model and the view of the game
 *
 * @author ddigges
 */
public class GameController {
    private View view;
    private GameEngine gameEngine;

    public GameController(View view, GameEngine gameEngine) {
        this.view = view;
        gameEngine.setMenuInputProvider((min, max) -> readIntInRange(min, max));
        gameEngine.setInputProvider(() -> view.readInput());
        gameEngine.setMessageDisplayer((arg) -> writeOutput(arg));
        gameEngine.setMenuDisplayer((arg) -> writeOutput(arg));
        this.gameEngine = gameEngine;
    }

    /**
     * Logic for displaying a menu object on the view
     * @param menu
     */
    public void writeOutput(Menu menu) {
        view.writeOutput(menu.getInstructionPrompt());

        for(int i=0; i<menu.getOptions().size(); ++i) {
            view.writeOutput(constructMenuItem(menu, i));
        }
    }

    /**
     * Wrapper fro calling the view method to display to the console
     * @param string
     */
    public void writeOutput(String string) {
        view.writeOutput(string);
    }

    /**
     * Determines if the string passed represents a valid integer
     * in the range [min, max]
     *
     * @param input
     * @param min
     * @param max
     * @return
     */
    public boolean isValidInt(String input, int min, int max) {
        if(input == null) {
            return false;
        }

        Integer integer = convertToInt(input);
        if(integer == null) {
            return false;
        }

        if(integer < min || integer > max) {
            return false;
        }
        return true;
    }


    /**
     * Polls the input for an integer in the range of
     * [min, max]
     * @param min
     * @param max
     * @return
     */
    public int readIntInRange(int min, int max) {
        String input = view.readInput();
        while(!isValidInt(input, min, max)) {
           view.writeOutput(MessageFormat.format("Invalid Input. Please enter a value between [{0}, {1}]", min, max));
           input = view.readInput();
        }
        return Integer.parseInt(input);
    }

    public void start() {
        gameEngine.start();
    }

    /**
     * Constructs the menu item to be displayed for a given string item
     * @param i
     * @return
     */
    private String constructMenuItem(Menu menu, int i) {
        return new StringBuilder()
                .append(i + menu.getOffset())
                .append(PERIOD)
                .append(SPACE)
                .append(menu.getOptions().get(i))
                .toString();
    }

    /**
     * Converts the given string to an integer
     *
     * If it cannot be converted, returns null
     * @param input
     * @return
     */
    private Integer convertToInt(String input) {
        try {
            return Integer.parseInt(input);
        } catch(NumberFormatException ex) {
            return null;
        }
    }
    public View getView() {
        return view;
    }

    public GameEngine getGameEngine() {
        return gameEngine;
    }

}
