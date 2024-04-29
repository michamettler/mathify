package ch.zhaw.mathify.model;

import ch.zhaw.mathify.model.exercise.ExerciseSubType;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.HashMap;
import java.util.Map;

/**
 * User model with username, level and experience
 * Every 100exp, the level will increase by one.
 * Username, password and email are required.
 */
public class User {
    private static final Logger LOG = LoggerFactory.getLogger(User.class);
    private String username;
    private int level;
    private int experience;
    private final HashMap<ExerciseSubType, Integer> technicalScore;
    private String guid;
    private String password;
    private String email;
    private Grade grade;
    private Role role;

    /**
     * @param username username of the user
     * @param email    email of the user
     * @param password password of the user
     *                 password will be hashed
     * @param grade    grade of the user
     *                 <p>
     *                 Default role is USER
     */
    public User(String username, String email, String password, Grade grade) {
        this.username = username;
        this.email = email;
        this.password = hashPassword(password);
        this.grade = grade;
        this.guid = createGuid();
        this.level = 1;
        this.technicalScore = initializeTechnicalScore();
        this.role = Role.USER;
    }

    /**
     * Default constructor used by Jackson object mapper
     */
    public User() {
        this.guid = createGuid();
        this.level = 1;
        this.experience = 0;
        this.technicalScore = initializeTechnicalScore();
        this.role = Role.USER;
        this.grade = Grade.NONE;
    }

    /**
     * @param exp amount of experience to add
     *            if exp >= 100, level will increase by 1
     */
    public void addExp(int exp) {
        LOG.debug("Adding {} experience to {}", exp, this.username);
        this.experience += exp;
        if (this.experience >= 100) {
            this.experience -= 100;
            level++;
        }
    }

    private static String createGuid() {
        LOG.debug("Creating GUID");
        return java.util.UUID.randomUUID().toString();
    }

    /**
     * @param password password to hash
     * @return hashed password
     */
    public static String hashPassword(String password) {
        LOG.debug("Hashing password");
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    /**
     * @param password password to verify
     * @param hash     hash to verify against
     * @return true if the password matches the hash
     */
    public static boolean verifyPassword(String password, String hash) {
        LOG.debug("Verifying password");
        try{
            return BCrypt.checkpw(password, hash);
        } catch (IllegalArgumentException e) {
            LOG.error("Invalid hash", e);
            return false;
        }
    }

    private HashMap<ExerciseSubType, Integer> initializeTechnicalScore() {
        HashMap<ExerciseSubType, Integer> technicalScore = new HashMap<>();
        for (ExerciseSubType subType : ExerciseSubType.values()) {
            technicalScore.put(subType, 1);
        }
        return technicalScore;
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

    public String getGuid() {
        return guid;
    }

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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Map<ExerciseSubType, Integer> getTechnicalScore() {
        return technicalScore;
    }

    public void setTechnicalScore(ExerciseSubType subType, int score) {
        technicalScore.put(subType, score);
    }

    /**
     * Update user with new values
     * @param user user to update
     */
    public void updateUser(User user) {
        if(user.getUsername() != null) setUsername(user.getUsername());
        if(user.getEmail() != null) setEmail(user.getEmail());
        if(user.getPassword() != null) setPassword(hashPassword(user.getPassword()));
        if(user.getGrade() != null) setGrade(user.getGrade());
        if(user.getRole() != null) setRole(user.getRole());
        if(user.getLevel() != 0) setLevel(user.getLevel());
        if(user.getExperience() != 0) setExperience(user.getExperience());
    }
}
