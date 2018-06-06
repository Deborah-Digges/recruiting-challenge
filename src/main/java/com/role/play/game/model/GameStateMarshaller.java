package com.role.play.game.model;

/**
 * Responsible for saving and restoring the state of the game
 */
public interface GameStateMarshaller {
    /**
     * Saves the game state to the given
     * path
     * @param gameState
     * @param path
     */
    void save(GameState gameState, String path);

    /**
     * Restores and returns the game state from the given file path
     * @param path
     * @return
     */
    GameState restore(String path);
}
