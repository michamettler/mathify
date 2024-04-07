package ch.zhaw.mathify.model.exercise;

import java.util.Arrays;

/**
 * @param result the result of the exercise
 * @param exercise the exercise as a string
 */
public record MathAdvancedExercise(int[] result, String exercise) implements Exercise {
    @Override
    public String toString(){
        return exercise;
    }

    @Override
    public boolean equals(Object obj){
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof MathAdvancedExercise other)) {
            return false;
        }
        return Arrays.equals(result, other.result) && exercise.equals(other.exercise);
    }

    @Override
    public int hashCode(){
        return Arrays.hashCode(result) + exercise.hashCode();
    }
}
