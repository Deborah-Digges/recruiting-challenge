package com.role.play.game.model.entities;

/**
 *
 * @author ddigges
 */
public class Player extends Character{
    /**
     * User selected type
     */
    private String type;


    /**
     * Optional gender
     */
    private String gender;

    public Player(PlayerBuilder builder) {
        super(builder.xp, builder.name);
        this.type = builder.type;
        this.gender = builder.gender;
    }

    public String getType() {
        return type;
    }

    public String getGender() {
        return gender;
    }

    public static class PlayerBuilder {
        /**
         * User provided name
         */
        private String name;

        /**
         * User selected type
         */
        private String type;


        /**
         * Optional gender
         */
        private String gender;

        /**
         * Experience
         */
        private int xp;

        public PlayerBuilder(String name, String type) {
            this.name = name;
            this.type = type;
        }

        public PlayerBuilder() {
        }

        public PlayerBuilder setGender(String gender) {
            this.gender = gender;
            return this;
        }
        public PlayerBuilder setType(String type) {
            this.type = type;
            return this;
        }

        public PlayerBuilder setXp(int xp) {
            this.xp = xp;
            return this;
        }

        public Player build() {
            return new Player(this);
        }
    }
}
