package ch.zhaw.mathify.model;

import org.springframework.security.crypto.bcrypt.BCrypt;
import ch.zhaw.mathify.controller.AccessManager;

/**
 * User model with username, level and experience
 * Every 100exp, the level will increase by one
 */
public class User {
    private String username;
    private int level;
    private int experience;
    private String guid;
    private String password;
    private String email;
    private Grade grade;
    private AccessManager.Role role;

    /**
     * @param username username of the user
     * @param email email of the user
     * @param password password of the user
     */
    public User(String username, String email, String password, Grade grade) {
        this.username = username;
        this.email = email;
        this.password = hashPassword(password);
        this.grade = grade;
        this.guid = createGuid();
        this.level = 1;
        this.role = AccessManager.Role.USER;
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
        this.experience += exp;
        if (this.experience >= 100) {
            this.experience -= 100;
            level++;
        }
    }

    private static String createGuid(){
        return java.util.UUID.randomUUID().toString();
    }

    /**
     * @param password password to hash
     * @return hashed password
     */
    public static String hashPassword(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    /**
     * @param password password to verify
     * @param hash hash to verify against
     * @return  true if the password matches the hash
     */
    public static boolean verifyPassword(String password, String hash){
        return BCrypt.checkpw(password, hash);
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
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public void setGuid(String guid) {
        this.guid = guid;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public AccessManager.Role getRole() {
        return role;
    }

    public void setRole(AccessManager.Role role) {
        this.role = role;
    }
}
