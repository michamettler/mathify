package ch.zhaw.mathify.math;

public class ExerciseFactory {
    private ExerciseFactory() {}
    public static Exercise createExercise(ExerciseType type, Grade grade){
        switch(type){
            case ARITHMETIC -> {
                return new ArithmeticExercise(grade);
            }
        }
        throw new RuntimeException("Unknown exercise type");
    }
}
