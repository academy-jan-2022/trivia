package com.adaptionsoft.games.uglytrivia;

import java.util.LinkedList;

public class PopCategory implements ICategory {

    public CategoryName name = CategoryName.POP;
    private LinkedList<String> categoryQuestions = new LinkedList<>();

    @Override
    public String getNextQuestion() {
        return categoryQuestions.removeFirst();
    }

    @Override
    public void createQuestions() {
        for (int i = 0; i < 50; i++) {
            String question = name.getValue() + " Question " + i;
            categoryQuestions.addLast(question);
        }
    }

    @Override
    public String getName(){

        return name.getValue();
    }
}