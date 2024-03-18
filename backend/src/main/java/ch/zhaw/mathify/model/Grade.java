package ch.zhaw.mathify.model;

/**
 * Enum for the different grades 1-6
 */
public enum Grade {
    FIRST(20), SECOND(100), THIRD(1000), FOURTH(10000), FIFTH(100000), SIXTH(1000000);
    private final int max;
    Grade(int max) {
        this.max = max;
    }

    public int getMax() {
        return max;
    }
}
