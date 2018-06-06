# Recruiting test

Build a role playing game for the CLI

## Instructions to install and run

1. `git clone https://github.com/Deborah-Digges/recruiting-challenge`
2. `cd recruiting-challenge`
3. `mvn clean install`
4. `java -jar target/role-play-game-1.0-SNAPSHOT-jar-with-dependencies.jar`

## Rules of the game

You can do one of three things:
1. Create a player and start a new game
2. Resume an old game
3. Exit

### 1. Create a player

1.1 Enter your name

1.2 Choose an avatar

1.3 Choose a gender

1.4 Begin playing

### Explore

1. Follow the map by hitting enter.
2. When presented with a set of options, enter your choice to move to the next node

### Save
1. At any point, if you wish to save, enter `Q`
2. Then, enter the name of the file where you would like to backup your game to

## Extending the game

1. New stories can be created by adding on nodes in `GameConfiguration.java`
2. New levels can also thus be created

## Code coverage

1. Run the code coverage report: `mvn jacoco:report`
2. Open the report: `open target/site/jacoco/index.html`