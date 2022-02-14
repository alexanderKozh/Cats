package com.lex.cat.model.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum CatColor {

    BLACK("black"),
    WHITE("white"),
    BLACK_AND_WHITE("black & white"),
    RED("red"),
    RED_AND_WHITE("red & white"),
    RED_AND_BLACK_AND_WHITE("red & black & white");

    private final String name;

    CatColor(String color) {
        this.name = color;
    }

    @JsonValue
    public String getName() {
        return name;
    }

    @JsonCreator
    public static CatColor fromName(String value) {
        for (CatColor color : CatColor.values()) {
            if (color.getName().equals(value)) {
                return color;
            }
        }

        if (value != null) {
            throw new IllegalArgumentException("Cannot find enum value for name: " + value);
        } else {
            return null;
        }
    }
}
