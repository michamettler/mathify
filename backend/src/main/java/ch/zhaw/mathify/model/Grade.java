package ch.zhaw.mathify.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Enum for the different grades 1-6
 */
public enum Grade {
    @JsonProperty("first")
    FIRST(20),
    @JsonProperty("second")
    SECOND(100),
    @JsonProperty("third")
    THIRD(1000),
    @JsonProperty("fourth")
    FOURTH(10000),
    @JsonProperty("fifth")
    FIFTH(100000),
    @JsonProperty("sixth")
    SIXTH(1000000),
    @JsonProperty("none")
    NONE(0);
    private final int max;

    Grade(int max) {
        this.max = max;
    }

    public int getMax() {
        return max;
    }
}
