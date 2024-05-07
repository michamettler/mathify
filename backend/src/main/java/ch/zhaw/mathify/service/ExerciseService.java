package ch.zhaw.mathify.service;

import ch.zhaw.mathify.maths.ExerciseGenerator;
import ch.zhaw.mathify.model.Grade;
import ch.zhaw.mathify.model.User;
import ch.zhaw.mathify.model.exercise.Exercise;
import ch.zhaw.mathify.model.exercise.ExerciseSubType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ch.zhaw.mathify.model.exercise.ExerciseSubType.*;

/**
 * This class is responsible for creating exercises for users with the right parameters.
 */
public class ExerciseService {
    private static final Logger LOG = LoggerFactory.getLogger(ExerciseService.class);
    private static final Map<Grade, List<ExerciseSubType>> GRADE_EXERCISE_SUB_TYPE_MAP = new EnumMap<>(Grade.class);
    private static final int TECHNICAL_SCORE_INCREASE = 1;
    private static final int TECHNICAL_SCORE_DECREASE = -1;
    private static final int EXPERIENCE_INCREASE = 10;
    private static final int EXPERIENCE_DECREASE = -1;
    private final Map<User, Integer> userExerciseCount = new HashMap<>();

    /**
     * Constructor for the ExerciseService.
     */
    public ExerciseService() {
        populateGradeExerciseSubtypeMapping();
    }

    /**
     * Creates an exercise for the given user.
     *
     * @param user The user for which the exercise should be created
     * @return A randomly generated exercise for the given user
     */
    public Exercise createExerciseForUser(User user) {
        LOG.info("Creating exercise for user: {}", user.getUsername());

        ExerciseSubType exerciseSubType = getExerciseSubTypeForUser(user);

        int technicalScore = user.getTechnicalScore().entrySet().stream().filter(entry -> entry.getKey().equals(exerciseSubType)).mapToInt(Map.Entry::getValue).findFirst().orElse(1);

        return ExerciseGenerator.generate(user.getGrade(), exerciseSubType, technicalScore);
    }

    /**
     * @param exercise The exercise to verify
     * @param user     The user who solved the exercise
     * @return true if the exercise was solved correctly, false otherwise
     */
    public boolean verifyResult(Exercise exercise, User user) {
        if (exercise.verifyResult()) {
            LOG.info("User {} solved the exercise correctly", user.getUsername());
            user.addExp(EXPERIENCE_INCREASE);
            user.addTechnicalScore(exercise.exerciseSubType(), TECHNICAL_SCORE_INCREASE);
            return true;
        } else {
            LOG.info("User {} solved the exercise incorrectly", user.getUsername());
            user.addExp(-EXPERIENCE_DECREASE);
            user.addTechnicalScore(exercise.exerciseSubType(), TECHNICAL_SCORE_DECREASE);
            return false;
        }
    }

    private ExerciseSubType getExerciseSubTypeForUser(User user) {
        int count = userExerciseCount.getOrDefault(user, 0);
        ExerciseSubType exerciseSubType = GRADE_EXERCISE_SUB_TYPE_MAP.get(user.getGrade()).get(count % GRADE_EXERCISE_SUB_TYPE_MAP.size());
        userExerciseCount.put(user, count + 1);

        LOG.debug("ExerciseSubType for user {} is {}", user.getUsername(), exerciseSubType.name());

        return exerciseSubType;
    }

    private static void populateGradeExerciseSubtypeMapping() {
        GRADE_EXERCISE_SUB_TYPE_MAP.put(Grade.FIRST, List.of(ADDITION, SUBTRACTION, COMPARISON, NEIGHBORS, SORTING, NUMBERCOMPLETION, TENSCOMPARISON));
        GRADE_EXERCISE_SUB_TYPE_MAP.put(Grade.SECOND,
                Stream.concat(GRADE_EXERCISE_SUB_TYPE_MAP.get(Grade.FIRST).stream(),
                                Stream.of(DOUBLING, HALVING, THREESTEPADDITION, THREESTEPSUBTRACTION, MULTIPLICATION, MULTIPLICATIONTABLE, DIVISION))
                        .collect(Collectors.toList()));
        GRADE_EXERCISE_SUB_TYPE_MAP.put(Grade.THIRD,
                Stream.concat(GRADE_EXERCISE_SUB_TYPE_MAP.get(Grade.SECOND).stream(),
                                Stream.of(ROUNDINGTEN, LONGADDITION, LONGSUBTRACTION, LONGMULTIPLICATION, ORDEROFOPERATIONS))
                        .collect(Collectors.toList()));
        GRADE_EXERCISE_SUB_TYPE_MAP.put(Grade.FOURTH,
                Stream.concat(GRADE_EXERCISE_SUB_TYPE_MAP.get(Grade.THIRD).stream(),
                                Arrays.stream(ExerciseSubType.values()))
                        .collect(Collectors.toList()));
        GRADE_EXERCISE_SUB_TYPE_MAP.put(Grade.FIFTH,
                Stream.concat(GRADE_EXERCISE_SUB_TYPE_MAP.get(Grade.FOURTH).stream(),
                                Arrays.stream(ExerciseSubType.values()))
                        .collect(Collectors.toList()));
        GRADE_EXERCISE_SUB_TYPE_MAP.put(Grade.SIXTH,
                Stream.concat(GRADE_EXERCISE_SUB_TYPE_MAP.get(Grade.FIFTH).stream(),
                                Arrays.stream(ExerciseSubType.values()))
                        .collect(Collectors.toList()));
    }
}
