package com.role.play.game.controller;


import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.role.play.game.TestBeanDefinitions;
import com.role.play.game.model.entities.Menu;
import com.role.play.game.view.View;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


public class GameControllerTest {

    private GameController gameController;

    @BeforeMethod
    public void resetController() {
        gameController = TestBeanDefinitions.mainController();
    }


    @DataProvider(name = "isValidIntProvider")
    public Object[][] provideData() {
        return new Object[][]{
                // null input
                {null, 1, 5, false},

                // invalid integer
                {"abc", 1, 5, false},

                // integer greater than range
                {"6", 1, 5, false},

                // integer less than range
                {"0", 1, 5, false},

                // in range int
                {"4", 1, 5, true}
        };
    }

    /**
     * The method isValidInt is tested using the data provider `isValidIntProvider`
     * which tests the following inputs:
     *
     * 1. Null input
     * 2. Invalid integer in string
     * 3. Integer greater than range
     * 4. Integer less than range
     * 4. Integer within range
     *
     * @param input
     * @param min
     * @param max
     * @param expectedValue
     */
    @Test(dataProvider = "isValidIntProvider")
    public void test_isValid_nullString(String input, int min, int max, boolean expectedValue) {
        boolean actualValue = gameController.isValidInt(input, min, max);
        assertThat(actualValue).isEqualTo(expectedValue);
    }

    /**
     * The following values are provided:
     * null, 6, 5 and we verify that the input is polled until
     * a valid value within the range is entered(5)
     *
     * The valid value is then parsed as an integer and returned
     */
    @Test
    public void test_readIntInRange() {
        View view = gameController.getView();

        doReturn(null)
        .doReturn("6")
        .doReturn("5")
        .when(view).readInput();

        doNothing().when(view).writeOutput(anyString());

        int expectedValue = 5;
        int readValue = gameController.readIntInRange(1, 5);

        assertThat(readValue).isEqualTo(expectedValue);
    }

    /**
     * Verify that write output calls the view with the
     * same argument
     */
    @Test
    public void test_writeOutput_String() {
        View view = gameController.getView();

        String outputString = "output";

        gameController.writeOutput(outputString);

        verify(view).writeOutput(outputString);
    }

    @Test
    public void test_writeOutput_Menu() {
        View view = gameController.getView();

        Menu menu = gameController.getGameEngine().getGameConfiguration().getMainMenu();

        gameController.writeOutput(menu);

        verify(view, times(menu.getOptions().size() + 1)).writeOutput(anyString());
    }
}
