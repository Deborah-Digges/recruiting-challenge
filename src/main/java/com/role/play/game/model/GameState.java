package com.role.play.game.model;

import java.io.Serializable;
import java.util.List;

import com.role.play.game.config.GameConfiguration;
import com.role.play.game.model.entities.Character;
import com.role.play.game.model.entities.Level;
import com.role.play.game.model.entities.Node;

/**
 * Contains all information about a currently running game
 *
 * @author ddigges
 */
public class GameState implements Serializable{
    private GameConfiguration gameConfiguration;
    private Character player;
    private Node currentNode;

    private Level currentLevel;
    private List<Level> levels;

    public GameState(GameConfiguration gameConfiguration) {
        this.gameConfiguration = gameConfiguration;
        this.levels = gameConfiguration.getLevels();
        this.currentLevel = this.levels.get(0);
        this.currentNode = this.currentLevel.getStartNode();
    }

    public GameConfiguration getGameConfiguration() {
        return gameConfiguration;
    }

    public Character getPlayer() {
        return player;
    }

    public void setPlayer(Character player) {
        this.player = player;
    }

    public Node getCurrentNode() {
        return currentNode;
    }

    public void setCurrentNode(Node currentNode) {
        this.currentNode = currentNode;
    }

    public Level getCurrentLevel() {
        return currentLevel;
    }

}
