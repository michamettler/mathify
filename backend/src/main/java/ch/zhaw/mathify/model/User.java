package ch.zhaw.mathify.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * User model with username, level and experience
 * Every 100exp, the level will increase by one
 */
public class User {
    @JsonProperty(required = true)
    private String username;
    @JsonProperty
    private int level;
    @JsonProperty
    private int exp;
    @JsonProperty(required = true)
    private String guid;
    @JsonProperty(required = true)
    private String password;
    @JsonProperty(required = true)
    private String email;

    /**
     * @param username username of the user
     * @param email email of the user
     * @param password password of the user
     */
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.guid = CreateGuid();
        this.level = 1;
    }

    /**
     * Default constructor used by Jackson object mapper
     */
    public User() {
        this.guid = CreateGuid();
        this.level = 1;
        this.exp = 0;
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

    private static String CreateGuid(){
        return java.util.UUID.randomUUID().toString();
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

    public String getGuid() { return guid; }
}
