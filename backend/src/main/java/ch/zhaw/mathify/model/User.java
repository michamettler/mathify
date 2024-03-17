package ch.zhaw.mathify.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * User model with username, level and experience
 * Every 100exp, the level will increase by one
 */
public class User {
    @JsonProperty
    private String username;
    @JsonProperty
    private int level;
    @JsonProperty
    private int experience;

    /**
     * @param username username of the user
     */
    public User(String username) {
        this.username = username;
        this.level = 1;
    }

    /**
     * Default constructor used by Jackson object mapper
     */
    public User() {
        this.level = 1;
        this.experience = 0;
    }

    /**
     * @param exp amount of experience to add
     *            if exp >= 100, level will increase by 1
     */
    public void addExp(int exp) {
        this.experience += exp;
        if (this.experience >= 100) {
            this.experience -= 100;
            level++;
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }
}
