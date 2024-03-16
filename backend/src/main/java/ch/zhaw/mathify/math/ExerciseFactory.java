package ch.zhaw.mathify.math;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;

public class ExerciseFactory {
    private static final Logger LOG = LoggerFactory.getLogger(ExerciseFactory.class);
    private ExerciseFactory() {}
    public static Exercise createExercise(Class<? extends Exercise> exerciseClass) {
        LOG.info("Creating exercise of type {}", exerciseClass.getName());
        try {
            return exerciseClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            LOG.error("Could not create exercise", e);
            return null;
        }
    }
}
