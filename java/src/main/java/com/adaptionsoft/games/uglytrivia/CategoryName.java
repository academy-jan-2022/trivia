package com.adaptionsoft.games.uglytrivia;

enum CategoryName {
    POP("Pop"),
    SCIENCE("Science"),
    SPORTS("Sports"),
    ROCK("Rock");


    private final String value;

    CategoryName(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
