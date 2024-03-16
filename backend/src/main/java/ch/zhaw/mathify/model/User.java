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
    private int exp;

    /**
     * @param username username of the user
     */
    public User(String username) {
        this.username = username;
        this.level = 1;
    }

    /**
     * @param username username of the user
     * @param level    level of the user
     * @param exp      experience of the user
     */
    public User(String username, int level, int exp) {
        this.username = username;
        this.level = level;
        this.exp = exp;
    }

    /**
     * Default constructor used by Jackson object mapper
     */
    public User() {
    }

    /**
     * @param exp amount of experience to add
     *            if exp >= 100, level will increase by 1
     */
    public void addExp(int exp) {
        this.exp += exp;
        if (this.exp >= 100) {
            this.exp -= 100;
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

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }
}
