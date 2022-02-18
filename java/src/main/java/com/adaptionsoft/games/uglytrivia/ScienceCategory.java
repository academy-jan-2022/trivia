package com.adaptionsoft.games.uglytrivia;

import java.util.LinkedList;

public class ScienceCategory implements Category {

    public CategoryName name = CategoryName.SCIENCE;
    private final LinkedList<String> categoryQuestions = new LinkedList<>();

    ScienceCategory(){
        for (int i = 0; i < 50; i++) {
            String question = name.getValue() + " Question " + i;
            categoryQuestions.addLast(question);
        }
    }

    @Override
    public String getNextQuestion() {
        return categoryQuestions.removeFirst();
    }


    @Override
    public String getName(){

        return name.getValue();
    }
}