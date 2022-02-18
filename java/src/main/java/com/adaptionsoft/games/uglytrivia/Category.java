package com.adaptionsoft.games.uglytrivia;

import java.util.LinkedList;

public class Category {

    public LinkedList<String> categoryQuestions = new LinkedList<>();

    enum CategoryName {
        Pop,
        Science,
        Sports,
        Rock
    }

    public void createQuestion(CategoryName category) {

        for (int i = 0; i < 50; i++) {
            String question = category + " Question " + i;
            categoryQuestions.addLast(question);
        }
    }
}
