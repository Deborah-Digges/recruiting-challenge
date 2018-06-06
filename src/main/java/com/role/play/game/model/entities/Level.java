package com.role.play.game.model.entities;

import java.io.Serializable;

public class Level implements Serializable{
    private Node startNode;

    public Level(Node startNode) {
        this.startNode = startNode;
    }

    public Node getStartNode() {
        return startNode;
    }
}
