package ch.zhaw.mathify.math.exercise;

import ch.zhaw.mathify.math.expression.Expression;
import ch.zhaw.mathify.model.Grade;

public abstract class Exercise {
    private Grade grade;
    private ExerciseSubType subType;
    private Expression expression;
    protected void build(Grade grade, ExerciseSubType subType){
        this.grade = grade;
        this.subType = subType;
    }
}
