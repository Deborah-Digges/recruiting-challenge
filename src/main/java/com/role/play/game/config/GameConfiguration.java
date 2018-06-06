package com.role.play.game.config;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.role.play.game.model.entities.Character;
import com.role.play.game.model.entities.Enemy;
import com.role.play.game.model.entities.Level;
import com.role.play.game.model.entities.Menu;
import com.role.play.game.model.entities.MenuImpl;
import com.role.play.game.model.entities.Node;
import com.role.play.game.model.entities.Player;

/**
 * The aim is to make the game data driven, so that new nodes and levels can be added in the game
 * just through modifying configuration and without code change. In future, this will be stored in JSON
 * files and read using the Jackson mapper.
 *
 * @author ddigges
 */
public class GameConfiguration implements Serializable {
    private String nameQuery = "Enter your name";
    private String saveFileQuery = "Enter the fileName to which to persist your game:";
    private String loadFileQuery = "Enter the fileName from which to restore the game:";

    private String attackMessage = "{0} with Xp={1} fights enemy {2} with Xp={3}";
    private String successMessage = "You were successful! Your Xp is now {0}";
    private String failureMessage = "Oh no! You lost. Your Xp is now {0}";

    private Menu mainMenu = new MenuImpl("Welcome to Deborah's Role Playing Game", Arrays.asList("Create a new player and play", "Resume an older game", "Exit"));
    private Menu playerOptionsMenu = new MenuImpl("Choose a player", Arrays.asList("troll", "knight", "Clown", "healer", "sorcerer"));
    private Menu genderOptionsMenu = new MenuImpl("Choose a gender", Arrays.asList("Male", "Female", "Other"));

    private List<Character> playerPersonas = new ArrayList<>(Arrays.asList(
            new Player.PlayerBuilder().setType("troll").setXp(10).build(),
            new Player.PlayerBuilder().setType("knight").setXp(100).build(),
            new Player.PlayerBuilder().setType("clown").setXp(20).build(),
            new Player.PlayerBuilder().setType("healer").setXp(200).build(),
            new Player.PlayerBuilder().setType("sorcerer").setXp(150).build()

    ));
    private List<Character> enemies = new ArrayList<>(Arrays.asList(
            new Enemy("ghost", Arrays.asList(
                    "I'm here to spook you out! Boo,",
                    "Greetings from the underworld",
                    "It's kind of fun being immortal"), 100),
            new Enemy("dragon", Arrays.asList(
                    "Ready to feel the heat of my fiery breath?",
                    "Brave men do not kill dragons",
                    "I am the dragon and I will eat you whole"), 1000),
            new Enemy("wolf", Arrays.asList(
                    "My teeth are so sharp"), 500)

    ));

    private Node end = new Node.NodeBuilder().setStoryLine("You have made it through the first perils of the forest. Let us adventure further").build();

    private Node wolf = new Node.NodeBuilder().setStoryLine("Fight the wolf").setCharacter(enemies.get(2))
            .setChildren(Arrays.asList(end))
            .build();

    private Node ghost = new Node.NodeBuilder().setStoryLine("Wrangle with the ghost").setCharacter(enemies.get(0))
            .setChildren(Arrays.asList(end))
            .build();

    private Node node3 = new Node.NodeBuilder().setStoryLine("The peace of the forest betrays two lurking dangers: A wolf and a ghost")
            .setChildren(Arrays.asList(wolf, ghost))
            .build();

    private Node node2 = new Node.NodeBuilder().setStoryLine("As we wind our way through the thick trees, look around and see the innocuous life around, everything looks peaceful on the surface")
            .setChildren(Arrays.asList(node3))
            .build();

    private Node node1 = new Node.NodeBuilder().setStoryLine("Welcome to the enchanted forest. You are about to embark on the journey of a lifetime")
            .setChildren(Arrays.asList(node2))
            .build();
    private Level level1 = new Level(node1);

    private List<Level> levels = Arrays.asList(level1);

    public String getNameQuery() {
        return nameQuery;
    }

    public Menu getMainMenu() {
        return mainMenu;
    }

    public Menu getPlayerOptionsMenu() {
        return playerOptionsMenu;
    }

    public Menu getGenderOptionsMenu() {
        return genderOptionsMenu;
    }

    public List<Level> getLevels() {
        return levels;
    }

    public String getSaveFileQuery() {
        return saveFileQuery;
    }

    public String getLoadFileQuery() {
        return loadFileQuery;
    }

    public List<Character> getPlayerPersonas() {
        return playerPersonas;
    }

    public String getAttackMessage() {
        return attackMessage;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public String getFailureMessage() {
        return failureMessage;
    }
}
