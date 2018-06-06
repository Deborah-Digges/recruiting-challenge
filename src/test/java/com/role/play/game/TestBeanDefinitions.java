package com.role.play.game;

import com.role.play.game.config.GameConfiguration;
import com.role.play.game.controller.GameController;
import com.role.play.game.model.GameEngine;
import com.role.play.game.model.GameState;
import com.role.play.game.model.GameStateMarshaller;
import com.role.play.game.view.ConsoleView;
import com.role.play.game.view.View;

import static org.mockito.Mockito.mock;

/**
 * Sets up the object dependencies
 *
 * @author ddigges
 */
public class TestBeanDefinitions {

    public static GameController mainController() {
        return new GameController(consoleView(), gameEngine());
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