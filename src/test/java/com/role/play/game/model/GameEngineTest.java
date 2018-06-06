package com.role.play.game.model;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.role.play.game.TestBeanDefinitions;
import com.role.play.game.model.entities.Character;
import com.role.play.game.model.entities.Menu;
import com.role.play.game.model.entities.Node;
import com.role.play.game.model.entities.Player;

import static com.role.play.game.common.Constants.SAVE_COMMAND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class GameEngineTest {
    private GameEngine gameEngine;
    private Character player;

    @BeforeMethod
    public void setup() {
        gameEngine = TestBeanDefinitions.gameEngine();
        gameEngine.setInputProvider(mock(Supplier.class));
        gameEngine.setMenuInputProvider(mock(BiFunction.class));
        gameEngine.setMenuDisplayer(mock(Consumer.class));
        gameEngine.setMessageDisplayer(mock(Consumer.class));

        doNothing().when(gameEngine.getMenuDisplayer()).accept(any(Menu.class));
        doNothing().when(gameEngine.getMessageDisplayer()).accept(anyString());
        doNothing().when(gameEngine.getGameStateMarshaller()).save(any(GameState.class), anyString());
    }

    /**
     * Test the player creation flow and one complete run through the game
     * by simulating user input
     */
    @Test
    public void testPlayerCreationAndPlayFlow() {
        int playerType = 2;
        int genderType = 2;
        int pathSelection = 2;
        String playerName = "Deborah";

        doReturn(1) // Select Play Menu
        .doReturn(playerType)            // Select first player type
        .doReturn(genderType)            // Select Male
        .doReturn(pathSelection)            // Select wolf path
                .when(gameEngine.getMenuInputProvider())
                .apply(anyInt(), anyInt());

        doReturn(playerName) // name input
        .doReturn("\n") // traverse to second node
        .doReturn("\n") // traverse to third node
        .doReturn("\n") // traverse to last node
        .when(gameEngine.getInputProvider())
        .get();

        gameEngine.start();

        assertThat(gameEngine.getGameState().getCurrentNode()).isNull();
        assertPlayerAttributes(gameEngine, gameEngine.getGameState().getPlayer(), playerType, genderType, playerName);
    }

    /**
     * Test the flow where the user creates a player and saves the game in the middle
     */
    @Test
    public void testPlayerCreationSaveFlow() {
        int playerType = 1;
        int genderType = 1;
        String playerName = "Deborah";
        String path = "file.obj";

        doReturn(1) // Select Play Menu
                .doReturn(playerType)            // Select first player type
                .doReturn(genderType)            // Select Male
                .when(gameEngine.getMenuInputProvider())
                .apply(anyInt(), anyInt());

        doReturn(playerName) // name input
                .doReturn("\n") // traverse to second node
                .doReturn(SAVE_COMMAND) // At the second node, issue the save command
                .doReturn(path)
                .when(gameEngine.getInputProvider())
                .get();

        gameEngine.start();

        // Verify that the player was created
        this.player = gameEngine.getGameState().getPlayer();
        assertPlayerAttributes(gameEngine, this.player, playerType, genderType, playerName);


        // Verify that the game state stopped at the right node
        assertThat(gameEngine.getGameState().getCurrentNode()).isEqualTo(getNode(2, gameEngine.getGameState()));

        // Verify that the save operation is called
        verify(gameEngine.getGameStateMarshaller()).save(gameEngine.getGameState(), path);

    }

    /**
     * Restart the game from the second node
     */
    @Test
    public void testRestoreFlow() {
        // Set the restored node to be on the second node
        gameEngine.getGameState().setCurrentNode(getNode(2, gameEngine.getGameState()));
        gameEngine.getGameState().setPlayer(this.player);

        int playerType = 1;
        int genderType = 1;
        String playerName = "Deborah";
        String path = "file.obj";
        int pathSelection = 1;

        doReturn(2) // Select Restore Menu
                .doReturn(pathSelection)            // Select wolf path
                .when(gameEngine.getMenuInputProvider())
                .apply(anyInt(), anyInt());

        doReturn(path) // file name Input
                .doReturn("\n") // traverse to third node
                .doReturn("\n") // traverse to last node
                .when(gameEngine.getInputProvider())
                .get();

        doReturn(gameEngine.getGameState())
                .when(gameEngine.getGameStateMarshaller())
                .restore(anyString());

        gameEngine.start();

        // Verify that the game completed
        assertThat(gameEngine.getGameState().getCurrentNode()).isEqualTo(null);
    }

    /**
     * Get first node at level i
     * @param i
     * @param gameState
     * @return
     */
    private Node getNode(int i, GameState gameState) {
        Node startNode = gameState.getGameConfiguration().getLevels().get(0).getStartNode();
        int count = 0;
        while(startNode != null && count < i - 1) {
            count += 1;
            if(startNode.getChildren() != null) {
                startNode = startNode.getChildren().get(0);
            } else {
                break;
            }
        }
        return startNode;
    }

    /**
     * assert that all the player attributes have been set during creation
     * @param gameEngine
     * @param player
     * @param playerType
     * @param genderType
     * @param playerName
     */
    private void assertPlayerAttributes(GameEngine gameEngine, Character player, int playerType, int genderType, String playerName) {
        assertThat(player).isNotNull();
        assertThat(player).isInstanceOf(Character.class);

        Player character = (Player) player;
        assertThat(((Player) player).getName()).isEqualTo(playerName);
        assertThat(character.getXp()).isEqualTo(gameEngine.lookupCharacterByType(character.getType()).getXp());
        assertThat(character.getType()).isEqualTo(gameEngine.getGameConfiguration().getPlayerOptionsMenu().getOption(playerType));
        assertThat(character.getGender()).isEqualTo(gameEngine.getGameConfiguration().getGenderOptionsMenu().getOption(genderType));
    }
}
