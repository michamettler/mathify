package ch.zhaw.mathify.math;

import ch.zhaw.mathify.model.Grade;

public abstract class Exercise {
    private Grade grade;
    public abstract double getResult();
    protected void build(Grade grade){
        this.grade = grade;
    }
}
