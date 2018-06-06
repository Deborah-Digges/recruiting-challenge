package com.role.play.game;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.role.play.game.config.GameConfiguration;
import com.role.play.game.controller.GameController;
import com.role.play.game.io.ConsoleInputHandler;
import com.role.play.game.model.GameEngine;
import com.role.play.game.model.GameState;
import com.role.play.game.model.GameStateMarshaller;
import com.role.play.game.model.GameStateMarshallerImpl;
import com.role.play.game.view.ConsoleView;
import com.role.play.game.view.View;

/**
 * Sets up the object dependencies
 *
 * @author ddigges
 */
public class BeanDefinitions {

    public static GameController mainController() {
        return new GameController(consoleView(), gameEngine());
    }

    public static GameEngine gameEngine() {
        return new GameEngine(gameState(), gameStateMarshaller());
    }

    public static GameStateMarshaller gameStateMarshaller() {
        return new GameStateMarshallerImpl();
    }

    public static GameState gameState() {
        return new GameState(gameConfiguration());
    }

    public static GameConfiguration gameConfiguration() {
        return new GameConfiguration();
    }

    public static View consoleView() {
        return new ConsoleView(consoleInputHandler());
    }

    public static ConsoleInputHandler consoleInputHandler() {
        return new ConsoleInputHandler(bufferedReader());
    }

    public static BufferedReader bufferedReader() {
        return new BufferedReader(inputStreamReader());
    }

    public static InputStreamReader inputStreamReader() {
        return new InputStreamReader(System.in);
    }
}
