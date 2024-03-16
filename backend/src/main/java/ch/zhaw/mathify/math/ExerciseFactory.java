package ch.zhaw.mathify.math;

import ch.zhaw.mathify.model.Grade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;

public class ExerciseFactory {
    private static final Logger LOG = LoggerFactory.getLogger(ExerciseFactory.class);
    private ExerciseFactory() {}
    public static Exercise createExercise(Class<? extends Exercise> exerciseClass, Grade grade) {
        LOG.info("Creating exercise of type {}", exerciseClass.getName());
        try {
            Exercise exercise = exerciseClass.getDeclaredConstructor().newInstance();
            exercise.build(grade);
            return exercise;
        } catch (InstantiationException e) {
            LOG.error("Failed to instantiate exercise class. Is it abstract or an interface?", e);
        } catch (IllegalAccessException e) {
            LOG.error("Failed to access the exercise class constructor. Is it accessible?", e);
        } catch (InvocationTargetException e) {
            LOG.error("Failed to invoke the exercise class constructor.", e);
        } catch (NoSuchMethodException e) {
            LOG.error("No suitable constructor found for exercise class.", e);
        }
        throw new ExerciseTypeNotFound("Failed to create exercise of type " + exerciseClass.getName());
    }
}
