package ch.zhaw.mathify.model;

/**
 * User model with username, level and experience
 * Every 100exp, the level will increase by one
 */
public class User {
    private String username;
    private int level;
    private int exp;

    public User(String username) {
        this.username = username;
        this.level = 1;
    }

    /**
     * @param exp amount of experience to add
     *            if exp >= 100, level will increase by 1
     */
    public void addExp(int exp) {
        this.exp += exp;
        if (this.exp >= 100) {
            this.exp -= 100;
            this.level++;
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
