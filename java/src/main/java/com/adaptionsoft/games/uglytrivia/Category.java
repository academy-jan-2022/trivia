package com.adaptionsoft.games.uglytrivia;

import java.util.LinkedList;

public class Category {

    public CategoryName name;
    private LinkedList<String> categoryQuestions = new LinkedList<>();

    public Category(CategoryName name) {

        this.name = name;
    }

    public String getNextQuestion() {
        return categoryQuestions.removeFirst();
    }

    public void createQuestions() {

        for (int i = 0; i < 50; i++) {
            String question = name.getValue() + " Question " + i;
            categoryQuestions.addLast(question);
        }
    }

    public String getName(){

        return name.getValue();
    }
}
