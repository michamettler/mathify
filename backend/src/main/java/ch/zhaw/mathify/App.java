package ch.zhaw.mathify;

import ch.zhaw.mathify.math.ArithmeticExercise;
import ch.zhaw.mathify.math.Exercise;
import ch.zhaw.mathify.math.ExerciseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {
    private static final Logger LOG = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        LOG.info("Starting app...");
        Exercise ex = ExerciseFactory.createExercise(ArithmeticExercise.class);
        LOG.info("Result of exercise: {}", ex.getResult());
    }
}
