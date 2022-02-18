package com.adaptionsoft.games.uglytrivia;

import java.util.Arrays;
import java.util.LinkedList;

public class PopCategory implements Category {

    public CategoryName name = CategoryName.POP;
    private final LinkedList<String> categoryQuestions = new LinkedList<>();
    private final int[] positions;

    PopCategory(int[] positions){
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