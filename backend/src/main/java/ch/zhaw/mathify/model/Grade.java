package ch.zhaw.mathify.model;

public enum Grade {
    FIRST(20), SECOND(100), THIRD(1000), FOURTH(10000), FIFTH(100000), SIXTH(1000000);
    private final int MAX;
    Grade(int max) {
        MAX = max;
    }

    public int getMax() {
        return MAX;
    }
}
