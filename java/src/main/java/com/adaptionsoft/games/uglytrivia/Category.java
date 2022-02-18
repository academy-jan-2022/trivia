package com.adaptionsoft.games.uglytrivia;

import java.util.LinkedList;

public class Category {

    public CategoryName categoryType;
    private LinkedList<String> categoryQuestions = new LinkedList<>();

    public Category(CategoryName name) {
        this.categoryType = name;
    }

    public String getNextQuestion() {
        return categoryQuestions.removeFirst();
    }

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

    public String getName(){
        return categoryType.name();
    }
}
