package com.role.play.game.model.entities;

import java.io.Serializable;
import java.util.List;

/**
 * Represents a logical menu along with it's associated data like
 * options, and prompt
 *
 * @author ddigges
 */
public class MenuImpl implements Menu,Serializable {
    /**
     * The message preceding the menu instructing the user
     */
    private String instructionPrompt;

    /**
     * List of options presented to the user
     */
    private List<String> options;

    /**
     * Offset added to item index
     */
    private static final int OFFSET = 1;

    public MenuImpl(String instructionPrompt, List<String> options) {
        this.instructionPrompt = instructionPrompt;
        this.options = options;
    }

    public int findIndexOfItem(String item) {
        return getOptions().indexOf(item) + OFFSET;
    }

    public String getOption(int index) {
        return this.options.get(index - OFFSET);
    }

    public List<String> getOptions() {
        return this.options;
    }

    public String getInstructionPrompt() {
        return this.instructionPrompt;
    }

    public int getOffset() {
        return OFFSET;
    }
}
