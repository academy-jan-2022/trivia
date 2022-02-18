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

    public void createQuestions() {

        for (int i = 0; i < 50; i++) {
            String question = categoryType + " Question " + i;
            categoryQuestions.addLast(question);
        }
    }

    public String getName(){
        return categoryType.name();
    }
}
