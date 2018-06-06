package com.role.play.game.model.entities;

import java.util.List;

/**
 * Defines operations supported by a menu
 *
 * @author ddigges
 */
public interface Menu {
    /**
     * Return the instruction prompt for the menu
     * @return
     */
    String getInstructionPrompt();

    /**
     *
     * @return
     */
    int getOffset();
    /**
     * Returns the options for the menu
     * @return
     */
    List<String> getOptions();

    /** Returns the index of a given item in the menu
     *
     * @param item: the item whose index is to be found
     */
    int findIndexOfItem(String item);

    /**
     * Return the option for a given index
     * @param index
     * @return
     */
    String getOption(int index);
}
