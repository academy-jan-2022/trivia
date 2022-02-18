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

    public String createQuestion(CategoryName category, int index) {
        String question = category + " Question " + index;

        categoryQuestions.addLast(question);

        return question;
    }
}
