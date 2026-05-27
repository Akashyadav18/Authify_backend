package com.Security.Authify.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum GenderEnum {
    MALE,
    FEMALE,
    OTHER;

    @JsonCreator
    public static GenderEnum from(String value) {
        return GenderEnum.valueOf(value.toUpperCase());
    }
}
