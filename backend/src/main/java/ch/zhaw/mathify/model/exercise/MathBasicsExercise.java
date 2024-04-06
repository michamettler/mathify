package ch.zhaw.mathify.model.exercise;

import java.util.Arrays;

public record MathBasicsExercise(int[] result, String exercise) implements Exercise {
    @Override
    public String toString(){
        return exercise;
    }

    @Override
    public boolean equals(Object obj){
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof MathBasicsExercise other)) {
            return false;
        }
        return Arrays.equals(result, other.result) && exercise.equals(other.exercise);
    }

    @Override
    public int hashCode(){
        return Arrays.hashCode(result) + exercise.hashCode();
    }
}
