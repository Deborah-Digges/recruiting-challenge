package com.role.play.game.model.entities;

import java.io.Serializable;

/**
 * Base class that any character in the game will inherit from
 *
 * @author ddigges
 */
public abstract class Character implements Serializable{
    private int xp;

    private String name;


    public Character(int xp, String name) {
        this.xp = xp;
        this.name = name;
    }

    /**
     * Returns whether this player won or not
     *
     * @param other: the character with whom to fight
     * @return
     */
    public boolean fight(Character other) {
        if(this.xp > other.xp) {
            this.xp +=1;
            other.xp -= 1;
            return true;
        } else if(this.xp < other.xp) {
            this.xp -=1;
            other.xp += 1;
            return false;
        } else {
            if(Math.floor(Math.random() * 5) == 4) {
                return true;
            } else {
                return false;
            }
        }
    }


    public int getXp() {
        return xp;
    }

    public String getName() {
        return name;
    }
}
