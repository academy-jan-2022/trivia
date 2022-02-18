package com.adaptionsoft.games.uglytrivia;

public class Category {

    enum CategoryName {
        Pop,
        Science,
        Sports,
        Rock
    }

    public String createQuestion(CategoryName category, int index) {
        return category + " Question " + index;
    }
}
