package ch.zhaw.mathify.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import at.favre.lib.crypto.bcrypt.BCrypt;

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
    private int experience;
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
        this.password = HashPassword(password);
        this.guid = CreateGuid();
        this.level = 1;
    }

    /**
     * Default constructor used by Jackson object mapper
     */
    public User() {
        this.guid = CreateGuid();
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

    private static String CreateGuid(){
        return java.util.UUID.randomUUID().toString();
    }

    private static String HashPassword(String password){
        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }

    /**
     * @param password password to verify
     * @param hash hash to verify against
     * @return  true if the password matches the hash
     */
    public static boolean VerifyPassword(String password, String hash){
        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), hash);
        return result.verified;
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

    public String getGuid() { return guid; }

    public void setPassword(String password) {
        this.password = HashPassword(password);
    }

    public String getPassword() {
        return password;
    }
}
