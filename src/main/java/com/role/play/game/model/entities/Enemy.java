package com.role.play.game.model.entities;

import java.io.Serializable;
import java.util.List;

/**
 *
 */
public class Enemy extends Character implements Serializable{
    private String name;

    /**
     * List of statements that the enemy will
     * exchange with the player
     */
    private List<String> exchanges;

    public Enemy(String name, List<String> exchanges, int xp) {
        super(xp, name);
        this.name = name;
        this.exchanges = exchanges;
    }
}
