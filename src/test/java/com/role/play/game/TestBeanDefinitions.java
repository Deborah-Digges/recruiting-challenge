package com.role.play.game;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.role.play.game.config.GameConfiguration;
import com.role.play.game.controller.MainController;
import com.role.play.game.io.ConsoleInputHandler;
import com.role.play.game.model.GameEngine;
import com.role.play.game.model.GameState;
import com.role.play.game.model.GameStateMarshaller;
import com.role.play.game.model.GameStateMarshallerImpl;
import com.role.play.game.view.ConsoleView;
import com.role.play.game.view.View;

import static org.mockito.Mockito.mock;

/**
 * Sets up the object dependencies
 *
 * @author ddigges
 */
public class TestBeanDefinitions {

    public static MainController mainController() {
        return new MainController(consoleView(), gameEngine());
    }

    public static GameEngine gameEngine() {
        return new GameEngine(gameState(), gameStateMarshaller());
    }

    public static GameStateMarshaller gameStateMarshaller() {
        return mock(GameStateMarshaller.class);
    }

    public static GameState gameState() {
        return new GameState(gameConfiguration());
    }

    public static GameConfiguration gameConfiguration() {
        return new GameConfiguration();
    }

    public static View consoleView() {
        return mock(ConsoleView.class);
    }

}