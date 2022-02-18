package com.adaptionsoft.games.uglytrivia;

import java.util.Arrays;
import java.util.LinkedList;

public class RockCategory implements Category {

    private final int[] positions;
    public CategoryName name = CategoryName.ROCK;
    private final LinkedList<String> categoryQuestions = new LinkedList<>();

    RockCategory(int[] positions){
        this.positions = positions;
        
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

    @Override
    public boolean shouldBeCurrent(int place) {
        return Arrays.stream(positions).anyMatch(position -> position == place);
    }
}