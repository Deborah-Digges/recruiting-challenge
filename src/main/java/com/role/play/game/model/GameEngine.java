package com.role.play.game.model;

import java.text.MessageFormat;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.role.play.game.config.GameConfiguration;
import com.role.play.game.model.entities.Character;
import com.role.play.game.model.entities.Menu;
import com.role.play.game.model.entities.Node;
import com.role.play.game.model.entities.Player;

import static com.role.play.game.common.Constants.SAVE_COMMAND;

/**
 * Actual logic for the game independent of the UI
 * with which it will interact
 *
 * @author ddigges
 */
public class GameEngine {
    private GameState gameState;
    private GameConfiguration gameConfiguration;
    private GameStateMarshaller gameStateMarshaller;

    private BiFunction<Integer, Integer, Integer> menuInputProvider;
    private Supplier<String> inputProvider;
    private Consumer<String> messageDisplayer;
    private Consumer<Menu> menuDisplayer;

    public GameEngine(GameState gameState, GameStateMarshaller gameStateMarshaller) {
        this.gameState = gameState;
        this.gameConfiguration = gameState.getGameConfiguration();
        this.gameStateMarshaller = gameStateMarshaller;
    }

    public String lookupInput(Menu menu, int input) {
        return menu.getOption(input);
    }

    /**
     * Initiates user input and calls appropriate functions
     */
    public void start() {
        Integer choice = getChoice(gameConfiguration.getMainMenu());
        switch (choice) {
            case 1:
                Player player = getPlayer();
                gameState.setPlayer(player);
                startGameLoop();
                break;
            case 2:
                messageDisplayer.accept(gameConfiguration.getLoadFileQuery());
                String path = inputProvider.get();
                GameState gameState = gameStateMarshaller.restore(path);
                setGameState(gameState);
                startGameLoop();
                break;
            case 3:
                return;
        }
    }

    /**
     * Initiates the game loop by looping over possible levels
     */
    private void startGameLoop() {
        Node currentNode = gameState.getCurrentNode();

        while(currentNode != null) {
            messageDisplayer.accept(currentNode.getStoryLine());
            String input = inputProvider.get();

            // Call the marshaller if we have reached a save command
            if (isSaveCommand(input)) {
                messageDisplayer.accept(gameConfiguration.getSaveFileQuery());
                String path = inputProvider.get();
                gameStateMarshaller.save(gameState, path);
                break;
            }

            // Return if we have reached the last node
            if (isLastNode(currentNode)) {
                currentNode = null;
                break;
            }

            // If there exists an enemy in the current node,
            // initiate attack
            if(currentNode.getCharacter() != null) {
                messageDisplayer.accept(attackMessage(this.gameState.getPlayer(), currentNode.getCharacter()));
                boolean success = getGameState().getPlayer().fight(currentNode.getCharacter());
                messageDisplayer.accept(getSuccessMessage(success, this.gameState.getPlayer()));
                if(gameState.getPlayer().getXp() <= 0) {
                    break;
                }
            }

            List<Node> children = currentNode.getChildren();
            Node nextNode;
            // There are multiple paths
            if (children.size() > 1) {
                showNodeOptions(currentNode);
                Integer nextNodeIndex = menuInputProvider.apply(1, children.size());
                nextNode = children.get(nextNodeIndex - 1);
                // If there is only one next node, just go to it
            } else {
                nextNode = children.get(0);
            }
            currentNode = nextNode;
            gameState.setCurrentNode(currentNode);
        }
        // update to null or next level if present
        gameState.setCurrentNode(currentNode);
    }

    private String getSuccessMessage(boolean success, Character player) {
        if(success) {
            return MessageFormat.format(gameConfiguration.getSuccessMessage(), player.getXp());
        } else {
            return MessageFormat.format(gameConfiguration.getFailureMessage(), player.getXp());
        }
    }

    /**
     * Builds the attack message
     */
    private String attackMessage(Character player, Character enemy) {
        return MessageFormat.format(gameConfiguration.getAttackMessage(),
                player.getName(), player.getXp(),
                enemy.getName(), enemy.getXp());
    }

    /**
     * Determines if the input is equal to the save command
     * @param input
     * @return
     */
    private boolean isSaveCommand(String input) {
        return input.equalsIgnoreCase(SAVE_COMMAND);
    }

    /**
     * Determines if the current node is the last node in the level
     * @param currentNode
     * @return
     */
    private boolean isLastNode(Node currentNode) {
        return currentNode.getChildren() == null;
    }

    /**
     * Displays all the children of a given node
     * @param currentNode
     */
    private void showNodeOptions(Node currentNode) {
        ListIterator<Node> iterator = currentNode.getChildren().listIterator();
        while (iterator.hasNext()) {
            messageDisplayer.accept(iterator.nextIndex() + 1  + ": " + iterator.next().getStoryLine());
        }
    }

    /**
     * Reads inputs for creating the player and returns a player instance
     * @return
     */
    private Player getPlayer() {
        messageDisplayer.accept(gameConfiguration.getNameQuery());
        String name = inputProvider.get();

        Integer playerSelection = getChoice(gameConfiguration.getPlayerOptionsMenu());

        Integer genderSelection = getChoice(gameConfiguration.getGenderOptionsMenu());

        return createPlayer(name, playerSelection, genderSelection);
    }

    /**
     * Creates player from the inputs passed by the user
     * @param name
     * @param playerSelection
     * @param genderSelection
     * @return
     */
    private Player createPlayer(String name, Integer playerSelection, Integer genderSelection) {
        String characterType = lookupInput(gameConfiguration.getPlayerOptionsMenu(), playerSelection);
        Character character = lookupCharacterByType(characterType);

        String genderType = lookupInput(gameConfiguration.getGenderOptionsMenu(), genderSelection);

        Player player = new Player.PlayerBuilder(
                name,
                characterType)
                .setXp(character.getXp())
                .setGender(genderType).build();

        return player;
    }

    /**
     * Looks up a character's persona in the configuration based on it's type
     * @param characterType
     * @return
     */
    public Character lookupCharacterByType(String characterType) {
        return gameConfiguration
                    .getPlayerPersonas()
                    .stream()
                    .filter((player -> ((Player)player).getType().equalsIgnoreCase(characterType))).findAny().get();
    }

    /**
     * Displays the menu to the user and returns
     * the choice selected by the user
     * @param menu
     * @return
     */
    private Integer getChoice(Menu menu) {
        menuDisplayer.accept(menu);
        return menuInputProvider.apply(menu.getOffset(), menu.getOptions().size());
    }

    public void setMenuInputProvider(BiFunction<Integer, Integer, Integer> menuInputProvider) {
        this.menuInputProvider = menuInputProvider;
    }

    public void setMessageDisplayer(Consumer<String> messageDisplayer) {
        this.messageDisplayer = messageDisplayer;
    }

    public void setMenuDisplayer(Consumer<Menu> menuDisplayer) {
        this.menuDisplayer = menuDisplayer;
    }

    public void setInputProvider(Supplier<String> inputProvider) {
        this.inputProvider = inputProvider;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public GameState getGameState() {
        return gameState;
    }

    public GameConfiguration getGameConfiguration() {
        return gameConfiguration;
    }

    public GameStateMarshaller getGameStateMarshaller() {
        return gameStateMarshaller;
    }

    public BiFunction<Integer, Integer, Integer> getMenuInputProvider() {
        return menuInputProvider;
    }

    public Supplier<String> getInputProvider() {
        return inputProvider;
    }

    public Consumer<String> getMessageDisplayer() {
        return messageDisplayer;
    }

    public Consumer<Menu> getMenuDisplayer() {
        return menuDisplayer;
    }
}
