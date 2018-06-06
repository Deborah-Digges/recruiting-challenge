package com.role.play.game.model.entities;

import java.io.Serializable;
import java.util.List;

/**
 * Container on the map
 * Can contain a character entity
 */
public class Node implements Serializable{
    private Character character;
    private String storyLine;
    private List<Node> children;

    /**
     * Constructor to create a tile with a
     * character on it
     * @param builder
     */
    public Node(NodeBuilder builder) {
        this.character = builder.character;
        this.storyLine = builder.storyLine;
        this.children = builder.children;
    }

    public Character getCharacter() {
        return character;
    }

    public String getStoryLine() {
        return storyLine;
    }

    public List<Node> getChildren() {
        return children;
    }

    public static class NodeBuilder {
        private Character character;
        private String storyLine;
        private List<Node> children;

        public NodeBuilder setCharacter(Character character) {
            this.character = character;
            return this;
        }

        public NodeBuilder setStoryLine(String storyLine) {
            this.storyLine = storyLine;
            return this;
        }

        public NodeBuilder setChildren(List<Node> children) {
            this.children = children;
            return this;
        }

        public Node build() {
            return new Node(this);
        }
    }
}
